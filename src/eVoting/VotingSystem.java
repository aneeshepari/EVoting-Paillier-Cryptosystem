package eVoting;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import jpaillier.HomomorphicOperations;
import jpaillier.KeyPair;
import jpaillier.KeyPairBuilder;
import jpaillier.PublicKey;

public class VotingSystem {
	private KeyPairBuilder keyGen;
	private KeyPair keyPair;
	public PublicKey publicKey;
	public int bits;
	private long maxVoters; // base
	private int maxCandidates;

	public VotingSystem(long maxVoters, int maxCandidates) {
		this.maxVoters = MathOperations.nextPowerof2(maxVoters);
		this.maxCandidates = maxCandidates;
		this.bits = EncryptionParameters.getMaxBits(maxVoters, maxCandidates);
		generateKeys();
	}

	public List<Long> tallyVotes(List<BigInteger> votes) {
		BigInteger sum = keyPair.decrypt(sumEncyptedVotes(votes));

		List<Long> counts = MathOperations.convertBase(sum, maxVoters);

		System.out.println("- Results -");
		for (int i = 0; i < counts.size(); i++) {
			System.out.println("Candidate " + i + ": " + counts.get(i));
		}

		return counts;
	}

	public BigInteger encryptVote(int candidate) {
		if (candidate < 0 || candidate >= maxCandidates) {
			System.err.println("candidate = " + candidate + " is invalid.");
			throw new IndexOutOfBoundsException();
		}
		BigInteger x = BigInteger.valueOf(maxVoters);
		return publicKey.encrypt(x.pow(candidate));
	}

	public long decryptVote(BigInteger vote) {
		return (long) MathOperations.log(keyPair.decrypt(vote), maxVoters);
	}

	private void generateKeys() {
		keyGen = new KeyPairBuilder();
		keyGen.bits(EncryptionParameters.getMaxBits(maxVoters, maxCandidates));
		this.keyPair = keyGen.generateKeyPair();
		this.publicKey = keyPair.getPublicKey();
	}

	private BigInteger sumEncyptedVotes(List<BigInteger> votes) {
		HomomorphicOperations homomorphicOperations = new HomomorphicOperations(publicKey);
		BigInteger sum = BigInteger.ONE;
		for (BigInteger vote : votes) {
			sum = homomorphicOperations.add(sum, vote);
		}
		return sum;
	}

	public static void main(String[] args) {
		long maxVoters = 9;
		int maxCandidates = 5;

		VotingSystem votingSystem = new VotingSystem(maxVoters, maxCandidates);

		List<BigInteger> votes = new ArrayList<>();

		votes.add(votingSystem.encryptVote(4));
		votes.add(votingSystem.encryptVote(3));
		votes.add(votingSystem.encryptVote(3));
		votes.add(votingSystem.encryptVote(3));
		votes.add(votingSystem.encryptVote(3));
		votes.add(votingSystem.encryptVote(3));
		votes.add(votingSystem.encryptVote(2));
		votes.add(votingSystem.encryptVote(2));
		votes.add(votingSystem.encryptVote(1));
		votes.add(votingSystem.encryptVote(1));
		votes.add(votingSystem.encryptVote(1));
		votes.add(votingSystem.encryptVote(0));
		votes.add(votingSystem.encryptVote(0));

		votingSystem.tallyVotes(votes);

		System.out.println(votingSystem.decryptVote(votingSystem.encryptVote(4)));
		System.out.println(votingSystem.decryptVote(votingSystem.encryptVote(3)));
		System.out.println(votingSystem.decryptVote(votingSystem.encryptVote(1)));
	}
}
