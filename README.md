# For exchanging keys between Server and Client(Hospital)

	1. server will send its public keys to client with hashValues of keys for verification

	2. get client keys encrypted using server public keys

	3. decrypt client keys using server private keys and store it

# Client(Hospital) sending data to Server

	1. Client will encrypt the data using server public key before sending

	2. server will decrypt the data using its private keys

	3. before appending the data server will encrypt the data using its private key

# Server sending data to Client(Hospital)

	1. Server will decrypt the data using its public key

	2. server will encrypt the data using client public keys

	3. client will decrypt the data using its private keys

