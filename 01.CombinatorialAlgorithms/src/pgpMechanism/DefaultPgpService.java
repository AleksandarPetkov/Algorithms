package pgpMechanism;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Iterator;

public class DefaultPgpService {
	private boolean asciiArmored = false;
	private boolean integrityCheck = true;

	@SuppressWarnings("unchecked")
	public static PGPPublicKey readPublicKey(InputStream in) throws IOException, PGPException {
		in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);

		PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(in, new JcaKeyFingerprintCalculator());

		//
		// we just loop through the collection till we find a key suitable for
		// encryption, in the real
		// world you would probably want to be a bit smarter about this.
		//
		PGPPublicKey key = null;

		//
		// iterate through the key rings.
		//
		Iterator<PGPPublicKeyRing> rIt = pgpPub.getKeyRings();

		while (key == null && rIt.hasNext()) {
			PGPPublicKeyRing kRing = rIt.next();
			Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
			while (key == null && kIt.hasNext()) {
				PGPPublicKey k = kIt.next();

				if (k.isEncryptionKey()) {
					key = k;
				}
			}
		}

		if (key == null) {
			throw new IllegalArgumentException("Can't find encryption key in key ring.");
		}

		return key;
	}

	/**
	 * Load a secret key ring collection from keyIn and find the secret key
	 * corresponding to keyID if it exists.
	 *
	 * @param keyIn input stream representing a key ring collection.
	 * @param keyID keyID we want.
	 * @param pass  passphrase to decrypt secret key with.
	 * @return
	 * @throws IOException
	 * @throws PGPException
	 * @throws NoSuchProviderException
	 */
	private static PGPPrivateKey findSecretKey(InputStream keyIn, long keyID, char[] pass)
			throws IOException, PGPException, NoSuchProviderException {
		PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
				org.bouncycastle.openpgp.PGPUtil.getDecoderStream(keyIn), new JcaKeyFingerprintCalculator());

		PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);

		if (pgpSecKey == null) {
			return null;
		}

		return pgpSecKey.extractPrivateKey(new JcePBESecretKeyDecryptorBuilder().setProvider("BC").build(pass));
	}

	/**
	 * decrypt the passed in message stream
	 */
	@SuppressWarnings("unchecked")
	public static void decryptFile(InputStream in, OutputStream out, InputStream keyIn, char[] passwd)
			throws Exception {
		Security.addProvider(new BouncyCastleProvider());

		in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);

		PGPObjectFactory pgpF = new PGPObjectFactory(in, new JcaKeyFingerprintCalculator());
		PGPEncryptedDataList enc;

		Object o = pgpF.nextObject();
		//
		// the first object might be a PGP marker packet.
		//
		if (o instanceof PGPEncryptedDataList) {
			enc = (PGPEncryptedDataList) o;
		} else {
			enc = (PGPEncryptedDataList) pgpF.nextObject();
		}

		//
		// find the secret key
		//
		Iterator<PGPPublicKeyEncryptedData> it = enc.getEncryptedDataObjects();
		PGPPrivateKey sKey = null;
		PGPPublicKeyEncryptedData pbe = null;

		while (sKey == null && it.hasNext()) {
			pbe = it.next();

			sKey = findSecretKey(keyIn, pbe.getKeyID(), passwd);
		}

		if (sKey == null) {
			throw new IllegalArgumentException("Secret key for message not found.");
		}

		InputStream clear = pbe.getDataStream(new BcPublicKeyDataDecryptorFactory(sKey));

		PGPObjectFactory plainFact = new PGPObjectFactory(clear, new JcaKeyFingerprintCalculator());

		Object message = plainFact.nextObject();

		if (message instanceof PGPCompressedData) {
			PGPCompressedData cData = (PGPCompressedData) message;
			PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream(), new JcaKeyFingerprintCalculator());

			message = pgpFact.nextObject();
		}

		if (message instanceof PGPLiteralData) {
			PGPLiteralData ld = (PGPLiteralData) message;

			InputStream unc = ld.getInputStream();
			int ch;

			while ((ch = unc.read()) >= 0) {
				out.write(ch);
			}
		} else if (message instanceof PGPOnePassSignatureList) {
			throw new PGPException("Encrypted message contains a signed message - not literal data.");
		} else {
			throw new PGPException("Message is not a simple encrypted file - type unknown.");
		}

		if (pbe.isIntegrityProtected()) {
			if (!pbe.verify()) {
				throw new PGPException("Message failed integrity check");
			}
		}
	}

	public static void encryptFile(OutputStream out, String fileName, PGPPublicKey encKey, boolean armor,
			boolean withIntegrityCheck) throws IOException, NoSuchProviderException, PGPException {
		Security.addProvider(new BouncyCastleProvider());

		if (armor) {
			out = new ArmoredOutputStream(out);
		}

		ByteArrayOutputStream bOut = new ByteArrayOutputStream();

		PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);

		org.bouncycastle.openpgp.PGPUtil.writeFileToLiteralData(comData.open(bOut), PGPLiteralData.BINARY,
				new File(fileName));

		comData.close();

		PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(
				new JcePGPDataEncryptorBuilder(SymmetricKeyAlgorithmTags.CAST5).setWithIntegrityPacket(true)
						.setSecureRandom(new SecureRandom()).setProvider("BC"));

		cPk.addMethod(new JcePublicKeyKeyEncryptionMethodGenerator(encKey));

		byte[] bytes = bOut.toByteArray();

		OutputStream cOut = cPk.open(out, bytes.length);

		cOut.write(bytes);

		cOut.close();

		out.close();
	}

	public void main(String[] test) throws Exception {
		DefaultPgpService defaultPgpService = new DefaultPgpService();

		String publicKeyFile = "C:\\Users\\luca.iavarone\\Downloads\\pgpKeys\\pub.asc";
		String privateKeyFile = "C:\\Users\\luca.iavarone\\Downloads\\pgpKeys\\sec.asc";
		String inputFile = "C:\\Users\\luca.iavarone\\Downloads\\pgpKeys\\inputFile.txt";
		String outputFile = "C:\\Users\\luca.iavarone\\Downloads\\pgpKeys\\outputFile.txt";
		String outputDecryptedFile = "C:\\Users\\luca.iavarone\\Downloads\\pgpKeys\\outputFileDecrypted.txt";
		String passphrase = "test1";

		FileInputStream publicKeyIn = new FileInputStream(publicKeyFile);
		FileOutputStream out = new FileOutputStream(outputFile);
		defaultPgpService.encryptFile(out, inputFile, defaultPgpService.readPublicKey(publicKeyIn), false, true);
		out.close();
		publicKeyIn.close();

		FileInputStream in2 = new FileInputStream(outputFile);
		FileInputStream keyIn = new FileInputStream(privateKeyFile);
		FileOutputStream outDecrypted = new FileOutputStream(outputDecryptedFile);
		defaultPgpService.decryptFile(in2, outDecrypted, keyIn, passphrase.toCharArray());
		in2.close();
		outDecrypted.close();
		keyIn.close();
	}
}
