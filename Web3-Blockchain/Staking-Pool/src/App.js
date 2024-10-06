import React, { useState, useEffect } from "react";
import Web3 from "web3";
import { ethers } from "ethers";
import "./App.css";

function App() {
  const [walletConnected, setWalletConnected] = useState(false);
  const [signer, setSigner] = useState(null);
  const [inputValue, setInputValue] = useState(""); // Value from user input
  const [provider, setProvider] = useState(null);
  const [network, setNetwork] = useState(null);
  const [bnbPrice, setBnbPrice] = useState(null);
  const [bnbBalance, setBnbBalance] = useState(null);

  const contractAddress = "0x520448A36Fa478AC55a2faF970c495C63cC3Fe23";
  const contractABI = [
    {
      "inputs": [],
      "stateMutability": "nonpayable",
      "type": "constructor"
    },
    {
      "anonymous": false,
      "inputs": [
        {
          "indexed": true,
          "internalType": "address",
          "name": "previousOwner",
          "type": "address"
        },
        {
          "indexed": true,
          "internalType": "address",
          "name": "newOwner",
          "type": "address"
        }
      ],
      "name": "OwnershipTransferred",
      "type": "event"
    },
    {
      "anonymous": false,
      "inputs": [
        {
          "indexed": true,
          "internalType": "address",
          "name": "tokenA",
          "type": "address"
        },
        {
          "indexed": true,
          "internalType": "address",
          "name": "tokenB",
          "type": "address"
        },
        {
          "indexed": false,
          "internalType": "uint256",
          "name": "rewardRate",
          "type": "uint256"
        }
      ],
      "name": "PoolCreated",
      "type": "event"
    },
    {
      "anonymous": false,
      "inputs": [
        {
          "indexed": true,
          "internalType": "address",
          "name": "user",
          "type": "address"
        },
        {
          "indexed": false,
          "internalType": "uint256",
          "name": "amountIn",
          "type": "uint256"
        },
        {
          "indexed": false,
          "internalType": "uint256",
          "name": "amountOut",
          "type": "uint256"
        }
      ],
      "name": "TokensSwapped",
      "type": "event"
    },
    {
      "anonymous": false,
      "inputs": [
        {
          "indexed": true,
          "internalType": "address",
          "name": "recipient",
          "type": "address"
        },
        {
          "indexed": true,
          "internalType": "address",
          "name": "token",
          "type": "address"
        },
        {
          "indexed": false,
          "internalType": "uint256",
          "name": "amount",
          "type": "uint256"
        }
      ],
      "name": "TokensWithdrawn",
      "type": "event"
    },
    {
      "inputs": [
        {
          "internalType": "uint256",
          "name": "amountB",
          "type": "uint256"
        }
      ],
      "name": "addLiquidity",
      "outputs": [],
      "stateMutability": "nonpayable",
      "type": "function"
    },
    {
      "inputs": [
        {
          "internalType": "uint256",
          "name": "amountA",
          "type": "uint256"
        }
      ],
      "name": "calculateReturn",
      "outputs": [
        {
          "internalType": "uint256",
          "name": "",
          "type": "uint256"
        }
      ],
      "stateMutability": "view",
      "type": "function"
    },
    {
      "inputs": [
        {
          "internalType": "address",
          "name": "_tokenA",
          "type": "address"
        },
        {
          "internalType": "address",
          "name": "_tokenB",
          "type": "address"
        },
        {
          "internalType": "uint256",
          "name": "_rewardRate",
          "type": "uint256"
        }
      ],
      "name": "createPool",
      "outputs": [],
      "stateMutability": "nonpayable",
      "type": "function"
    },
    {
      "inputs": [
        {
          "internalType": "uint256",
          "name": "amountA",
          "type": "uint256"
        }
      ],
      "name": "investInPool",
      "outputs": [],
      "stateMutability": "nonpayable",
      "type": "function"
    },
    {
      "inputs": [],
      "name": "owner",
      "outputs": [
        {
          "internalType": "address",
          "name": "",
          "type": "address"
        }
      ],
      "stateMutability": "view",
      "type": "function"
    },
    {
      "inputs": [],
      "name": "renounceOwnership",
      "outputs": [],
      "stateMutability": "nonpayable",
      "type": "function"
    },
    {
      "inputs": [],
      "name": "rewardRate",
      "outputs": [
        {
          "internalType": "uint256",
          "name": "",
          "type": "uint256"
        }
      ],
      "stateMutability": "view",
      "type": "function"
    },
    {
      "inputs": [],
      "name": "tokenA",
      "outputs": [
        {
          "internalType": "contract IERC20",
          "name": "",
          "type": "address"
        }
      ],
      "stateMutability": "view",
      "type": "function"
    },
    {
      "inputs": [],
      "name": "tokenB",
      "outputs": [
        {
          "internalType": "contract IERC20",
          "name": "",
          "type": "address"
        }
      ],
      "stateMutability": "view",
      "type": "function"
    },
    {
      "inputs": [
        {
          "internalType": "address",
          "name": "newOwner",
          "type": "address"
        }
      ],
      "name": "transferOwnership",
      "outputs": [],
      "stateMutability": "nonpayable",
      "type": "function"
    },
    {
      "inputs": [
        {
          "internalType": "address",
          "name": "token",
          "type": "address"
        },
        {
          "internalType": "uint256",
          "name": "amount",
          "type": "uint256"
        },
        {
          "internalType": "address",
          "name": "recipient",
          "type": "address"
        }
      ],
      "name": "withdrawTokens",
      "outputs": [],
      "stateMutability": "nonpayable",
      "type": "function"
    }
  ];

  // Function to connect wallet
  async function connectWallet() {
    try {
      const web3 = new Web3(window.ethereum);
      const providerInstance = new ethers.providers.Web3Provider(window.ethereum);
      setProvider(providerInstance);

      const signerInstance = await providerInstance.getSigner();
      setSigner(signerInstance);

      const networkDetails = await providerInstance.getNetwork();
      setNetwork(networkDetails);

      const balance = await signerInstance.getBalance();
      setBnbBalance(ethers.utils.formatEther(balance)); // Convert balance to BNB

      setWalletConnected(true);
    } catch (error) {
      console.error("Error connecting wallet:", error);
    }
  }

  // Function to fetch live BNB price
  useEffect(() => {
    const fetchBnbPrice = async () => {
      try {
        const response = await fetch(
          "https://api.binance.com/api/v3/ticker/price?symbol=BNBUSDT"
        );
        const data = await response.json();
        setBnbPrice(parseFloat(data.price).toFixed(2)); // Set BNB price with 2 decimals
      } catch (error) {
        console.error("Error fetching BNB price:", error);
      }
    };
    fetchBnbPrice();
  }, []);

  // Function to reveal and send
  async function revealAndSend() {
    if (!inputValue) {
      alert("Please enter an amount to invest.");
      return;
    }

    try {
      const membership1 = new ethers.Contract(contractAddress, contractABI, signer);
      const response = await membership1.investInPool(ethers.utils.parseUnits(inputValue, 18)); // Convert value to correct format
      console.log("Transaction response:", response);
    } catch (error) {
      console.error("Error:", error);
    }
  }

  // MetaMask detection
  useEffect(() => {
    if (typeof window.ethereum === "undefined") {
      alert("MetaMask is not installed. Download it from here: https://metamask.io/download.html");
    }
  }, []);

  return (
    <div className="App">
      <div className="main-content">
        <h1>Welcome to Token Swap Pool</h1>
        <p>Swap your tokens and earn rewards!</p>

        {!walletConnected ? (
          <button className="btn-connect" onClick={connectWallet}>
            Connect Wallet
          </button>
        ) : (
          <>
            <h2>Connected to Network: {network?.name}</h2>
            <h3>Your Wallet BNB Balance: {bnbBalance} BNB</h3>

            <div className="input-container">
              <label htmlFor="invest-amount">Enter amount to invest:</label>
              <input
                type="number"
                id="invest-amount"
                value={inputValue}
                onChange={(e) => setInputValue(e.target.value)}
                placeholder="Enter amount"
              />
            </div>

            <button className="btn-submit" onClick={revealAndSend}>
              Invest Now
            </button>
          </>
        )}
      </div>

      <div className="sidebar">
        <h2>BNB Live Price</h2>
        <p>{bnbPrice ? `$${bnbPrice}` : "Loading..."}</p>
        <div className="metamask-link">
          {!walletConnected && (
            <a href="https://metamask.io/download.html" target="_blank" rel="noopener noreferrer">
              Download MetaMask
            </a>
          )}
        </div>
      </div>
    </div>
  );
}

export default App;
