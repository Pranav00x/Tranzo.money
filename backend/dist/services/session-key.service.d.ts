export declare class SessionKeyService {
    private static ENTRY_POINT;
    private static KERNEL_VERSION;
    static getPublicClient(): {
        account: {
            address: undefined;
            type: "json-rpc";
        };
        batch?: import("viem").ClientConfig["batch"] | undefined;
        cacheTime: number;
        ccipRead?: import("viem").ClientConfig["ccipRead"] | undefined;
        chain: {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        };
        dataSuffix?: import("viem").DataSuffix | undefined;
        experimental_blockTag?: import("viem").BlockTag | undefined;
        key: string;
        name: string;
        pollingInterval: number;
        request: import("viem").EIP1193RequestFn<[{
            Method: "web3_clientVersion";
            Parameters?: undefined;
            ReturnType: string;
        }, {
            Method: "web3_sha3";
            Parameters: [data: import("viem").Hash];
            ReturnType: string;
        }, {
            Method: "net_listening";
            Parameters?: undefined;
            ReturnType: boolean;
        }, {
            Method: "net_peerCount";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "net_version";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_blobBaseFee";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_blockNumber";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_call";
            Parameters: readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier, stateOverrideSet: import("viem").RpcStateOverride] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier, stateOverrideSet: import("viem").RpcStateOverride, blockOverrides: import("viem").RpcBlockOverrides];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_createAccessList";
            Parameters: [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>] | [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: {
                accessList: import("viem").AccessList;
                gasUsed: import("viem").Quantity;
            };
        }, {
            Method: "eth_chainId";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_coinbase";
            Parameters?: undefined;
            ReturnType: import("abitype").Address;
        }, {
            Method: "eth_estimateGas";
            Parameters: [transaction: import("viem").RpcTransactionRequest] | [transaction: import("viem").RpcTransactionRequest, block: import("viem").RpcBlockNumber | import("viem").BlockTag] | [transaction: import("viem").RpcTransactionRequest, block: import("viem").RpcBlockNumber | import("viem").BlockTag, stateOverride: import("viem").RpcStateOverride];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_fillTransaction";
            Parameters: [transaction: import("viem").RpcTransactionRequest];
            ReturnType: {
                capabilities?: Record<string, unknown> | undefined;
                raw: import("viem").Hex;
                tx: import("viem").RpcTransaction;
            };
        }, {
            Method: "eth_feeHistory";
            Parameters: [blockCount: import("viem").Quantity, newestBlock: import("viem").RpcBlockNumber | import("viem").BlockTag, rewardPercentiles: number[] | undefined];
            ReturnType: import("viem").RpcFeeHistory;
        }, {
            Method: "eth_gasPrice";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBalance";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBlockByHash";
            Parameters: [hash: import("viem").Hash, includeTransactionObjects: boolean];
            ReturnType: import("viem").RpcBlock | null;
        }, {
            Method: "eth_getBlockByNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, includeTransactionObjects: boolean];
            ReturnType: import("viem").RpcBlock | null;
        }, {
            Method: "eth_getBlockTransactionCountByHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBlockTransactionCountByNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getCode";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_getFilterChanges";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: import("viem").RpcLog[] | import("viem").Hex[];
        }, {
            Method: "eth_getFilterLogs";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: import("viem").RpcLog[];
        }, {
            Method: "eth_getLogs";
            Parameters: [{
                address?: import("abitype").Address | import("abitype").Address[] | undefined;
                topics?: import("viem").LogTopic[] | undefined;
            } & ({
                fromBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                toBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                blockHash?: undefined;
            } | {
                fromBlock?: undefined;
                toBlock?: undefined;
                blockHash?: import("viem").Hash | undefined;
            })];
            ReturnType: import("viem").RpcLog[];
        }, {
            Method: "eth_getProof";
            Parameters: [address: import("abitype").Address, storageKeys: import("viem").Hash[], block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").RpcProof;
        }, {
            Method: "eth_getStorageAt";
            Parameters: [address: import("abitype").Address, index: import("viem").Quantity, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_getTransactionByBlockHashAndIndex";
            Parameters: [hash: import("viem").Hash, index: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionByBlockNumberAndIndex";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, index: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionByHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionBySenderAndNonce";
            Parameters: [sender: import("abitype").Address, nonce: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionCount";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getTransactionReceipt";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").RpcTransactionReceipt | null;
        }, {
            Method: "eth_getUncleByBlockHashAndIndex";
            Parameters: [hash: import("viem").Hash, index: import("viem").Quantity];
            ReturnType: import("viem").RpcUncle | null;
        }, {
            Method: "eth_getUncleByBlockNumberAndIndex";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, index: import("viem").Quantity];
            ReturnType: import("viem").RpcUncle | null;
        }, {
            Method: "eth_getUncleCountByBlockHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getUncleCountByBlockNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_maxPriorityFeePerGas";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newBlockFilter";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newFilter";
            Parameters: [filter: {
                fromBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                toBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                address?: import("abitype").Address | import("abitype").Address[] | undefined;
                topics?: import("viem").LogTopic[] | undefined;
            }];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newPendingTransactionFilter";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_protocolVersion";
            Parameters?: undefined;
            ReturnType: string;
        }, {
            Method: "eth_sendRawTransaction";
            Parameters: [signedTransaction: import("viem").Hex];
            ReturnType: import("viem").Hash;
        }, {
            Method: "eth_sendRawTransactionSync";
            Parameters: [signedTransaction: import("viem").Hex] | [signedTransaction: import("viem").Hex, timeout: number];
            ReturnType: import("viem").RpcTransactionReceipt;
        }, {
            Method: "eth_simulateV1";
            Parameters: [{
                blockStateCalls: readonly {
                    blockOverrides?: import("viem").RpcBlockOverrides | undefined;
                    calls?: readonly import("viem").ExactPartial<import("viem").RpcTransactionRequest>[] | undefined;
                    stateOverrides?: import("viem").RpcStateOverride | undefined;
                }[];
                returnFullTransactions?: boolean | undefined;
                traceTransfers?: boolean | undefined;
                validation?: boolean | undefined;
            }, import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: readonly (import("viem").RpcBlock & {
                calls: readonly {
                    error?: {
                        data?: import("viem").Hex | undefined;
                        code: number;
                        message: string;
                    } | undefined;
                    logs?: readonly import("viem").RpcLog[] | undefined;
                    gasUsed: import("viem").Hex;
                    returnData: import("viem").Hex;
                    status: import("viem").Hex;
                }[];
            })[];
        }, {
            Method: "eth_uninstallFilter";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: boolean;
        }, ...any[]]>;
        transport: import("viem").TransportConfig<"http", import("viem").EIP1193RequestFn> & {
            fetchOptions?: import("viem").HttpTransportConfig["fetchOptions"] | undefined;
            url?: string | undefined;
        };
        type: string;
        uid: string;
        call: (parameters: import("viem").CallParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>) => Promise<import("viem").CallReturnType>;
        createAccessList: (parameters: import("viem").CreateAccessListParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>) => Promise<{
            accessList: import("viem").AccessList;
            gasUsed: bigint;
        }>;
        createBlockFilter: () => Promise<import("viem").CreateBlockFilterReturnType>;
        createContractEventFilter: <const abi extends import("abitype").Abi | readonly unknown[], eventName extends import("viem").ContractEventName<abi> | undefined, args extends import("viem").MaybeExtractEventArgsFromAbi<abi, eventName> | undefined, strict extends boolean | undefined = undefined, fromBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, toBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined>(args: import("viem").CreateContractEventFilterParameters<abi, eventName, args, strict, fromBlock, toBlock>) => Promise<import("viem").CreateContractEventFilterReturnType<abi, eventName, args, strict, fromBlock, toBlock>>;
        createEventFilter: <const abiEvent extends import("abitype").AbiEvent | undefined = undefined, const abiEvents extends readonly import("abitype").AbiEvent[] | readonly unknown[] | undefined = abiEvent extends import("abitype").AbiEvent ? [abiEvent] : undefined, strict extends boolean | undefined = undefined, fromBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, toBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, _EventName extends string | undefined = import("viem").MaybeAbiEventName<abiEvent>, _Args extends import("viem").MaybeExtractEventArgsFromAbi<abiEvents, _EventName> | undefined = undefined>(args?: import("viem").CreateEventFilterParameters<abiEvent, abiEvents, strict, fromBlock, toBlock, _EventName, _Args> | undefined) => Promise<import("viem").CreateEventFilterReturnType<abiEvent, abiEvents, strict, fromBlock, toBlock, _EventName, _Args>>;
        createPendingTransactionFilter: () => Promise<import("viem").CreatePendingTransactionFilterReturnType>;
        estimateContractGas: <chain extends import("viem").Chain | undefined, const abi extends import("abitype").Abi | readonly unknown[], functionName extends import("viem").ContractFunctionName<abi, "nonpayable" | "payable">, args extends import("viem").ContractFunctionArgs<abi, "nonpayable" | "payable", functionName>>(args: import("viem").EstimateContractGasParameters<abi, functionName, args, chain>) => Promise<import("viem").EstimateContractGasReturnType>;
        estimateGas: (args: import("viem").EstimateGasParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>) => Promise<import("viem").EstimateGasReturnType>;
        fillTransaction: <chainOverride extends import("viem").Chain | undefined = undefined, accountOverride extends import("viem").Account | import("abitype").Address | undefined = undefined>(args: import("viem").FillTransactionParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, import("viem").Account, chainOverride, accountOverride>) => Promise<import("viem").FillTransactionReturnType<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride>>;
        getBalance: (args: import("viem").GetBalanceParameters) => Promise<import("viem").GetBalanceReturnType>;
        getBlobBaseFee: () => Promise<import("viem").GetBlobBaseFeeReturnType>;
        getBlock: <includeTransactions extends boolean = false, blockTag extends import("viem").BlockTag = "latest">(args?: import("viem").GetBlockParameters<includeTransactions, blockTag>) => Promise<{
            number: blockTag extends "pending" ? null : bigint;
            hash: blockTag extends "pending" ? null : `0x${string}`;
            nonce: blockTag extends "pending" ? null : `0x${string}`;
            logsBloom: blockTag extends "pending" ? null : `0x${string}`;
            baseFeePerGas: bigint | null;
            blobGasUsed: bigint;
            difficulty: bigint;
            excessBlobGas: bigint;
            extraData: import("viem").Hex;
            gasLimit: bigint;
            gasUsed: bigint;
            miner: import("abitype").Address;
            mixHash: import("viem").Hash;
            parentBeaconBlockRoot?: `0x${string}` | undefined;
            parentHash: import("viem").Hash;
            receiptsRoot: import("viem").Hex;
            sealFields: import("viem").Hex[];
            sha3Uncles: import("viem").Hash;
            size: bigint;
            stateRoot: import("viem").Hash;
            timestamp: bigint;
            totalDifficulty: bigint | null;
            transactionsRoot: import("viem").Hash;
            uncles: import("viem").Hash[];
            withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
            withdrawalsRoot?: `0x${string}` | undefined;
            transactions: includeTransactions extends true ? ({
                value: bigint;
                type: "deposit";
                yParity: number;
                from: import("abitype").Address;
                gas: bigint;
                hash: import("viem").Hash;
                input: import("viem").Hex;
                nonce: number;
                r: import("viem").Hex;
                s: import("viem").Hex;
                to: import("abitype").Address | null;
                typeHex: import("viem").Hex | null;
                v: bigint;
                gasPrice?: undefined | undefined;
                maxFeePerBlobGas?: undefined | undefined;
                maxFeePerGas: bigint;
                maxPriorityFeePerGas: bigint;
                isSystemTx?: boolean;
                mint?: bigint | undefined | undefined;
                sourceHash: import("viem").Hex;
                blockHash: (blockTag extends "pending" ? true : false) extends infer T ? T extends (blockTag extends "pending" ? true : false) ? T extends true ? null : `0x${string}` : never : never;
                blockNumber: (blockTag extends "pending" ? true : false) extends infer T_1 ? T_1 extends (blockTag extends "pending" ? true : false) ? T_1 extends true ? null : bigint : never : never;
                transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_2 ? T_2 extends (blockTag extends "pending" ? true : false) ? T_2 extends true ? null : number : never : never;
            } | {
                value: bigint;
                type: "legacy";
                yParity?: undefined | undefined;
                from: import("abitype").Address;
                gas: bigint;
                hash: import("viem").Hash;
                input: import("viem").Hex;
                nonce: number;
                r: import("viem").Hex;
                s: import("viem").Hex;
                to: import("abitype").Address | null;
                typeHex: import("viem").Hex | null;
                v: bigint;
                accessList?: undefined | undefined;
                authorizationList?: undefined | undefined;
                blobVersionedHashes?: undefined | undefined;
                chainId?: number | undefined;
                gasPrice: bigint;
                maxFeePerBlobGas?: undefined | undefined;
                maxFeePerGas?: undefined | undefined;
                maxPriorityFeePerGas?: undefined | undefined;
                isSystemTx?: undefined | undefined;
                mint?: undefined | undefined;
                sourceHash?: undefined | undefined;
                blockHash: (blockTag extends "pending" ? true : false) extends infer T_3 ? T_3 extends (blockTag extends "pending" ? true : false) ? T_3 extends true ? null : `0x${string}` : never : never;
                blockNumber: (blockTag extends "pending" ? true : false) extends infer T_4 ? T_4 extends (blockTag extends "pending" ? true : false) ? T_4 extends true ? null : bigint : never : never;
                transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_5 ? T_5 extends (blockTag extends "pending" ? true : false) ? T_5 extends true ? null : number : never : never;
            } | {
                value: bigint;
                type: "eip2930";
                yParity: number;
                from: import("abitype").Address;
                gas: bigint;
                hash: import("viem").Hash;
                input: import("viem").Hex;
                nonce: number;
                r: import("viem").Hex;
                s: import("viem").Hex;
                to: import("abitype").Address | null;
                typeHex: import("viem").Hex | null;
                v: bigint;
                accessList: import("viem").AccessList;
                authorizationList?: undefined | undefined;
                blobVersionedHashes?: undefined | undefined;
                chainId: number;
                gasPrice: bigint;
                maxFeePerBlobGas?: undefined | undefined;
                maxFeePerGas?: undefined | undefined;
                maxPriorityFeePerGas?: undefined | undefined;
                isSystemTx?: undefined | undefined;
                mint?: undefined | undefined;
                sourceHash?: undefined | undefined;
                blockHash: (blockTag extends "pending" ? true : false) extends infer T_6 ? T_6 extends (blockTag extends "pending" ? true : false) ? T_6 extends true ? null : `0x${string}` : never : never;
                blockNumber: (blockTag extends "pending" ? true : false) extends infer T_7 ? T_7 extends (blockTag extends "pending" ? true : false) ? T_7 extends true ? null : bigint : never : never;
                transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_8 ? T_8 extends (blockTag extends "pending" ? true : false) ? T_8 extends true ? null : number : never : never;
            } | {
                value: bigint;
                type: "eip1559";
                yParity: number;
                from: import("abitype").Address;
                gas: bigint;
                hash: import("viem").Hash;
                input: import("viem").Hex;
                nonce: number;
                r: import("viem").Hex;
                s: import("viem").Hex;
                to: import("abitype").Address | null;
                typeHex: import("viem").Hex | null;
                v: bigint;
                accessList: import("viem").AccessList;
                authorizationList?: undefined | undefined;
                blobVersionedHashes?: undefined | undefined;
                chainId: number;
                gasPrice?: undefined | undefined;
                maxFeePerBlobGas?: undefined | undefined;
                maxFeePerGas: bigint;
                maxPriorityFeePerGas: bigint;
                isSystemTx?: undefined | undefined;
                mint?: undefined | undefined;
                sourceHash?: undefined | undefined;
                blockHash: (blockTag extends "pending" ? true : false) extends infer T_9 ? T_9 extends (blockTag extends "pending" ? true : false) ? T_9 extends true ? null : `0x${string}` : never : never;
                blockNumber: (blockTag extends "pending" ? true : false) extends infer T_10 ? T_10 extends (blockTag extends "pending" ? true : false) ? T_10 extends true ? null : bigint : never : never;
                transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_11 ? T_11 extends (blockTag extends "pending" ? true : false) ? T_11 extends true ? null : number : never : never;
            } | {
                value: bigint;
                type: "eip4844";
                yParity: number;
                from: import("abitype").Address;
                gas: bigint;
                hash: import("viem").Hash;
                input: import("viem").Hex;
                nonce: number;
                r: import("viem").Hex;
                s: import("viem").Hex;
                to: import("abitype").Address | null;
                typeHex: import("viem").Hex | null;
                v: bigint;
                accessList: import("viem").AccessList;
                authorizationList?: undefined | undefined;
                blobVersionedHashes: readonly import("viem").Hex[];
                chainId: number;
                gasPrice?: undefined | undefined;
                maxFeePerBlobGas: bigint;
                maxFeePerGas: bigint;
                maxPriorityFeePerGas: bigint;
                isSystemTx?: undefined | undefined;
                mint?: undefined | undefined;
                sourceHash?: undefined | undefined;
                blockHash: (blockTag extends "pending" ? true : false) extends infer T_12 ? T_12 extends (blockTag extends "pending" ? true : false) ? T_12 extends true ? null : `0x${string}` : never : never;
                blockNumber: (blockTag extends "pending" ? true : false) extends infer T_13 ? T_13 extends (blockTag extends "pending" ? true : false) ? T_13 extends true ? null : bigint : never : never;
                transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_14 ? T_14 extends (blockTag extends "pending" ? true : false) ? T_14 extends true ? null : number : never : never;
            } | {
                value: bigint;
                type: "eip7702";
                yParity: number;
                from: import("abitype").Address;
                gas: bigint;
                hash: import("viem").Hash;
                input: import("viem").Hex;
                nonce: number;
                r: import("viem").Hex;
                s: import("viem").Hex;
                to: import("abitype").Address | null;
                typeHex: import("viem").Hex | null;
                v: bigint;
                accessList: import("viem").AccessList;
                authorizationList: import("viem").SignedAuthorizationList;
                blobVersionedHashes?: undefined | undefined;
                chainId: number;
                gasPrice?: undefined | undefined;
                maxFeePerBlobGas?: undefined | undefined;
                maxFeePerGas: bigint;
                maxPriorityFeePerGas: bigint;
                isSystemTx?: undefined | undefined;
                mint?: undefined | undefined;
                sourceHash?: undefined | undefined;
                blockHash: (blockTag extends "pending" ? true : false) extends infer T_15 ? T_15 extends (blockTag extends "pending" ? true : false) ? T_15 extends true ? null : `0x${string}` : never : never;
                blockNumber: (blockTag extends "pending" ? true : false) extends infer T_16 ? T_16 extends (blockTag extends "pending" ? true : false) ? T_16 extends true ? null : bigint : never : never;
                transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_17 ? T_17 extends (blockTag extends "pending" ? true : false) ? T_17 extends true ? null : number : never : never;
            })[] : `0x${string}`[];
        }>;
        getBlockNumber: (args?: import("viem").GetBlockNumberParameters | undefined) => Promise<import("viem").GetBlockNumberReturnType>;
        getBlockTransactionCount: (args?: import("viem").GetBlockTransactionCountParameters | undefined) => Promise<import("viem").GetBlockTransactionCountReturnType>;
        getBytecode: (args: import("viem").GetBytecodeParameters) => Promise<import("viem").GetBytecodeReturnType>;
        getChainId: () => Promise<import("viem").GetChainIdReturnType>;
        getCode: (args: import("viem").GetBytecodeParameters) => Promise<import("viem").GetBytecodeReturnType>;
        getContractEvents: <const abi extends import("abitype").Abi | readonly unknown[], eventName extends import("viem").ContractEventName<abi> | undefined = undefined, strict extends boolean | undefined = undefined, fromBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, toBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined>(args: import("viem").GetContractEventsParameters<abi, eventName, strict, fromBlock, toBlock>) => Promise<import("viem").GetContractEventsReturnType<abi, eventName, strict, fromBlock, toBlock>>;
        getDelegation: (args: import("viem").GetDelegationParameters) => Promise<import("viem").GetDelegationReturnType>;
        getEip712Domain: (args: import("viem").GetEip712DomainParameters) => Promise<import("viem").GetEip712DomainReturnType>;
        getEnsAddress: (args: import("viem").GetEnsAddressParameters) => Promise<import("viem").GetEnsAddressReturnType>;
        getEnsAvatar: (args: import("viem").GetEnsAvatarParameters) => Promise<import("viem").GetEnsAvatarReturnType>;
        getEnsName: (args: import("viem").GetEnsNameParameters) => Promise<import("viem").GetEnsNameReturnType>;
        getEnsResolver: (args: import("viem").GetEnsResolverParameters) => Promise<import("viem").GetEnsResolverReturnType>;
        getEnsText: (args: import("viem").GetEnsTextParameters) => Promise<import("viem").GetEnsTextReturnType>;
        getFeeHistory: (args: import("viem").GetFeeHistoryParameters) => Promise<import("viem").GetFeeHistoryReturnType>;
        estimateFeesPerGas: <chainOverride extends import("viem").Chain | undefined = undefined, type extends import("viem").FeeValuesType = "eip1559">(args?: import("viem").EstimateFeesPerGasParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride, type>) => Promise<import("viem").EstimateFeesPerGasReturnType<type>>;
        getFilterChanges: <filterType extends import("viem").FilterType, const abi extends import("abitype").Abi | readonly unknown[] | undefined, eventName extends string | undefined, strict extends boolean | undefined = undefined, fromBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, toBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined>(args: import("viem").GetFilterChangesParameters<filterType, abi, eventName, strict, fromBlock, toBlock>) => Promise<import("viem").GetFilterChangesReturnType<filterType, abi, eventName, strict, fromBlock, toBlock>>;
        getFilterLogs: <const abi extends import("abitype").Abi | readonly unknown[] | undefined, eventName extends string | undefined, strict extends boolean | undefined = undefined, fromBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, toBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined>(args: import("viem").GetFilterLogsParameters<abi, eventName, strict, fromBlock, toBlock>) => Promise<import("viem").GetFilterLogsReturnType<abi, eventName, strict, fromBlock, toBlock>>;
        getGasPrice: () => Promise<import("viem").GetGasPriceReturnType>;
        getLogs: <const abiEvent extends import("abitype").AbiEvent | undefined = undefined, const abiEvents extends readonly import("abitype").AbiEvent[] | readonly unknown[] | undefined = abiEvent extends import("abitype").AbiEvent ? [abiEvent] : undefined, strict extends boolean | undefined = undefined, fromBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined, toBlock extends import("viem").BlockNumber | import("viem").BlockTag | undefined = undefined>(args?: import("viem").GetLogsParameters<abiEvent, abiEvents, strict, fromBlock, toBlock> | undefined) => Promise<import("viem").GetLogsReturnType<abiEvent, abiEvents, strict, fromBlock, toBlock>>;
        getProof: (args: import("viem").GetProofParameters) => Promise<import("viem").GetProofReturnType>;
        estimateMaxPriorityFeePerGas: <chainOverride extends import("viem").Chain | undefined = undefined>(args?: {
            chain: chainOverride;
        }) => Promise<import("viem").EstimateMaxPriorityFeePerGasReturnType>;
        getStorageAt: (args: import("viem").GetStorageAtParameters) => Promise<import("viem").GetStorageAtReturnType>;
        getTransaction: <blockTag extends import("viem").BlockTag = "latest">(args: import("viem").GetTransactionParameters<blockTag>) => Promise<{
            value: bigint;
            type: "deposit";
            yParity: number;
            from: import("abitype").Address;
            gas: bigint;
            hash: import("viem").Hash;
            input: import("viem").Hex;
            nonce: number;
            r: import("viem").Hex;
            s: import("viem").Hex;
            to: import("abitype").Address | null;
            typeHex: import("viem").Hex | null;
            v: bigint;
            gasPrice?: undefined | undefined;
            maxFeePerBlobGas?: undefined | undefined;
            maxFeePerGas: bigint;
            maxPriorityFeePerGas: bigint;
            isSystemTx?: boolean;
            mint?: bigint | undefined | undefined;
            sourceHash: import("viem").Hex;
            blockHash: (blockTag extends "pending" ? true : false) extends infer T ? T extends (blockTag extends "pending" ? true : false) ? T extends true ? null : `0x${string}` : never : never;
            blockNumber: (blockTag extends "pending" ? true : false) extends infer T_1 ? T_1 extends (blockTag extends "pending" ? true : false) ? T_1 extends true ? null : bigint : never : never;
            transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_2 ? T_2 extends (blockTag extends "pending" ? true : false) ? T_2 extends true ? null : number : never : never;
        } | {
            value: bigint;
            type: "legacy";
            yParity?: undefined | undefined;
            from: import("abitype").Address;
            gas: bigint;
            hash: import("viem").Hash;
            input: import("viem").Hex;
            nonce: number;
            r: import("viem").Hex;
            s: import("viem").Hex;
            to: import("abitype").Address | null;
            typeHex: import("viem").Hex | null;
            v: bigint;
            accessList?: undefined | undefined;
            authorizationList?: undefined | undefined;
            blobVersionedHashes?: undefined | undefined;
            chainId?: number | undefined;
            gasPrice: bigint;
            maxFeePerBlobGas?: undefined | undefined;
            maxFeePerGas?: undefined | undefined;
            maxPriorityFeePerGas?: undefined | undefined;
            isSystemTx?: undefined | undefined;
            mint?: undefined | undefined;
            sourceHash?: undefined | undefined;
            blockHash: (blockTag extends "pending" ? true : false) extends infer T_3 ? T_3 extends (blockTag extends "pending" ? true : false) ? T_3 extends true ? null : `0x${string}` : never : never;
            blockNumber: (blockTag extends "pending" ? true : false) extends infer T_4 ? T_4 extends (blockTag extends "pending" ? true : false) ? T_4 extends true ? null : bigint : never : never;
            transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_5 ? T_5 extends (blockTag extends "pending" ? true : false) ? T_5 extends true ? null : number : never : never;
        } | {
            value: bigint;
            type: "eip2930";
            yParity: number;
            from: import("abitype").Address;
            gas: bigint;
            hash: import("viem").Hash;
            input: import("viem").Hex;
            nonce: number;
            r: import("viem").Hex;
            s: import("viem").Hex;
            to: import("abitype").Address | null;
            typeHex: import("viem").Hex | null;
            v: bigint;
            accessList: import("viem").AccessList;
            authorizationList?: undefined | undefined;
            blobVersionedHashes?: undefined | undefined;
            chainId: number;
            gasPrice: bigint;
            maxFeePerBlobGas?: undefined | undefined;
            maxFeePerGas?: undefined | undefined;
            maxPriorityFeePerGas?: undefined | undefined;
            isSystemTx?: undefined | undefined;
            mint?: undefined | undefined;
            sourceHash?: undefined | undefined;
            blockHash: (blockTag extends "pending" ? true : false) extends infer T_6 ? T_6 extends (blockTag extends "pending" ? true : false) ? T_6 extends true ? null : `0x${string}` : never : never;
            blockNumber: (blockTag extends "pending" ? true : false) extends infer T_7 ? T_7 extends (blockTag extends "pending" ? true : false) ? T_7 extends true ? null : bigint : never : never;
            transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_8 ? T_8 extends (blockTag extends "pending" ? true : false) ? T_8 extends true ? null : number : never : never;
        } | {
            value: bigint;
            type: "eip1559";
            yParity: number;
            from: import("abitype").Address;
            gas: bigint;
            hash: import("viem").Hash;
            input: import("viem").Hex;
            nonce: number;
            r: import("viem").Hex;
            s: import("viem").Hex;
            to: import("abitype").Address | null;
            typeHex: import("viem").Hex | null;
            v: bigint;
            accessList: import("viem").AccessList;
            authorizationList?: undefined | undefined;
            blobVersionedHashes?: undefined | undefined;
            chainId: number;
            gasPrice?: undefined | undefined;
            maxFeePerBlobGas?: undefined | undefined;
            maxFeePerGas: bigint;
            maxPriorityFeePerGas: bigint;
            isSystemTx?: undefined | undefined;
            mint?: undefined | undefined;
            sourceHash?: undefined | undefined;
            blockHash: (blockTag extends "pending" ? true : false) extends infer T_9 ? T_9 extends (blockTag extends "pending" ? true : false) ? T_9 extends true ? null : `0x${string}` : never : never;
            blockNumber: (blockTag extends "pending" ? true : false) extends infer T_10 ? T_10 extends (blockTag extends "pending" ? true : false) ? T_10 extends true ? null : bigint : never : never;
            transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_11 ? T_11 extends (blockTag extends "pending" ? true : false) ? T_11 extends true ? null : number : never : never;
        } | {
            value: bigint;
            type: "eip4844";
            yParity: number;
            from: import("abitype").Address;
            gas: bigint;
            hash: import("viem").Hash;
            input: import("viem").Hex;
            nonce: number;
            r: import("viem").Hex;
            s: import("viem").Hex;
            to: import("abitype").Address | null;
            typeHex: import("viem").Hex | null;
            v: bigint;
            accessList: import("viem").AccessList;
            authorizationList?: undefined | undefined;
            blobVersionedHashes: readonly import("viem").Hex[];
            chainId: number;
            gasPrice?: undefined | undefined;
            maxFeePerBlobGas: bigint;
            maxFeePerGas: bigint;
            maxPriorityFeePerGas: bigint;
            isSystemTx?: undefined | undefined;
            mint?: undefined | undefined;
            sourceHash?: undefined | undefined;
            blockHash: (blockTag extends "pending" ? true : false) extends infer T_12 ? T_12 extends (blockTag extends "pending" ? true : false) ? T_12 extends true ? null : `0x${string}` : never : never;
            blockNumber: (blockTag extends "pending" ? true : false) extends infer T_13 ? T_13 extends (blockTag extends "pending" ? true : false) ? T_13 extends true ? null : bigint : never : never;
            transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_14 ? T_14 extends (blockTag extends "pending" ? true : false) ? T_14 extends true ? null : number : never : never;
        } | {
            value: bigint;
            type: "eip7702";
            yParity: number;
            from: import("abitype").Address;
            gas: bigint;
            hash: import("viem").Hash;
            input: import("viem").Hex;
            nonce: number;
            r: import("viem").Hex;
            s: import("viem").Hex;
            to: import("abitype").Address | null;
            typeHex: import("viem").Hex | null;
            v: bigint;
            accessList: import("viem").AccessList;
            authorizationList: import("viem").SignedAuthorizationList;
            blobVersionedHashes?: undefined | undefined;
            chainId: number;
            gasPrice?: undefined | undefined;
            maxFeePerBlobGas?: undefined | undefined;
            maxFeePerGas: bigint;
            maxPriorityFeePerGas: bigint;
            isSystemTx?: undefined | undefined;
            mint?: undefined | undefined;
            sourceHash?: undefined | undefined;
            blockHash: (blockTag extends "pending" ? true : false) extends infer T_15 ? T_15 extends (blockTag extends "pending" ? true : false) ? T_15 extends true ? null : `0x${string}` : never : never;
            blockNumber: (blockTag extends "pending" ? true : false) extends infer T_16 ? T_16 extends (blockTag extends "pending" ? true : false) ? T_16 extends true ? null : bigint : never : never;
            transactionIndex: (blockTag extends "pending" ? true : false) extends infer T_17 ? T_17 extends (blockTag extends "pending" ? true : false) ? T_17 extends true ? null : number : never : never;
        }>;
        getTransactionConfirmations: (args: import("viem").GetTransactionConfirmationsParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>) => Promise<import("viem").GetTransactionConfirmationsReturnType>;
        getTransactionCount: (args: import("viem").GetTransactionCountParameters) => Promise<import("viem").GetTransactionCountReturnType>;
        getTransactionReceipt: (args: import("viem").GetTransactionReceiptParameters) => Promise<{
            blobGasPrice?: bigint | undefined;
            blobGasUsed?: bigint | undefined;
            blockHash: import("viem").Hash;
            blockNumber: bigint;
            blockTimestamp?: bigint | undefined;
            contractAddress: import("abitype").Address | null | undefined;
            cumulativeGasUsed: bigint;
            effectiveGasPrice: bigint;
            from: import("abitype").Address;
            gasUsed: bigint;
            logs: import("viem").Log<bigint, number, false>[];
            logsBloom: import("viem").Hex;
            root?: `0x${string}` | undefined;
            status: "success" | "reverted";
            to: import("abitype").Address | null;
            transactionHash: import("viem").Hash;
            transactionIndex: number;
            type: import("viem").TransactionType;
            l1GasPrice: bigint | null;
            l1GasUsed: bigint | null;
            l1Fee: bigint | null;
            l1FeeScalar: number | null;
        }>;
        multicall: <const contracts extends readonly unknown[], allowFailure extends boolean = true>(args: import("viem").MulticallParameters<contracts, allowFailure>) => Promise<import("viem").MulticallReturnType<contracts, allowFailure>>;
        prepareTransactionRequest: <const request extends import("viem").PrepareTransactionRequestRequest<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride>, chainOverride extends import("viem").Chain | undefined = undefined, accountOverride extends import("viem").Account | import("abitype").Address | undefined = undefined>(args: import("viem").PrepareTransactionRequestParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, import("viem").Account, chainOverride, accountOverride, request>) => Promise<import("viem").UnionRequiredBy<Extract<import("viem").UnionOmit<import("viem").ExtractChainFormatterParameters<import("viem").DeriveChain<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride>, "transactionRequest", import("viem").TransactionRequest>, "from"> & (import("viem").DeriveChain<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride> extends infer T_1 ? T_1 extends import("viem").DeriveChain<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride> ? T_1 extends import("viem").Chain ? {
            chain: T_1;
        } : {
            chain?: undefined;
        } : never : never) & (import("viem").DeriveAccount<import("viem").Account, accountOverride> extends infer T_2 ? T_2 extends import("viem").DeriveAccount<import("viem").Account, accountOverride> ? T_2 extends import("viem").Account ? {
            account: T_2;
            from: import("abitype").Address;
        } : {
            account?: undefined;
            from?: undefined;
        } : never : never), import("viem").IsNever<((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_3 ? T_3 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_3 extends "legacy" ? import("viem").TransactionRequestLegacy : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_4 ? T_4 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_4 extends "eip1559" ? import("viem").TransactionRequestEIP1559 : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_5 ? T_5 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_5 extends "eip2930" ? import("viem").TransactionRequestEIP2930 : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_6 ? T_6 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_6 extends "eip4844" ? import("viem").TransactionRequestEIP4844 : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_7 ? T_7 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_7 extends "eip7702" ? import("viem").TransactionRequestEIP7702 : never : never : never)> extends true ? unknown : import("viem").ExactPartial<((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_8 ? T_8 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_8 extends "legacy" ? import("viem").TransactionRequestLegacy : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_9 ? T_9 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_9 extends "eip1559" ? import("viem").TransactionRequestEIP1559 : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_10 ? T_10 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_10 extends "eip2930" ? import("viem").TransactionRequestEIP2930 : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_11 ? T_11 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_11 extends "eip4844" ? import("viem").TransactionRequestEIP4844 : never : never : never) | ((request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) extends infer T_12 ? T_12 extends (request["type"] extends string ? request["type"] : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)> extends "legacy" ? unknown : import("viem").GetTransactionType<request, (request extends {
            accessList?: undefined;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
        } & import("viem").FeeValuesLegacy ? "legacy" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } & (import("viem").OneOf<{
            maxFeePerGas: import("viem").FeeValuesEIP1559["maxFeePerGas"];
        } | {
            maxPriorityFeePerGas: import("viem").FeeValuesEIP1559["maxPriorityFeePerGas"];
        }, import("viem").FeeValuesEIP1559> & {
            accessList?: import("viem").TransactionSerializableEIP2930["accessList"] | undefined;
        }) ? "eip1559" : never) | (request extends {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: bigint;
            sidecars?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: undefined;
            maxPriorityFeePerGas?: undefined;
        } & {
            accessList: import("viem").TransactionSerializableEIP2930["accessList"];
        } ? "eip2930" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: undefined;
            blobs?: readonly `0x${string}`[] | readonly import("viem").ByteArray[];
            blobVersionedHashes?: readonly `0x${string}`[];
            maxFeePerBlobGas?: bigint;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: false | readonly import("viem").BlobSidecar<`0x${string}`>[];
        }) & (import("viem").ExactPartial<import("viem").FeeValuesEIP4844> & import("viem").OneOf<{
            blobs: import("viem").TransactionSerializableEIP4844["blobs"];
        } | {
            blobVersionedHashes: import("viem").TransactionSerializableEIP4844["blobVersionedHashes"];
        } | {
            sidecars: import("viem").TransactionSerializableEIP4844["sidecars"];
        }, import("viem").TransactionSerializableEIP4844>) ? "eip4844" : never) | (request extends ({
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        } | {
            accessList?: import("viem").AccessList;
            authorizationList?: import("viem").SignedAuthorizationList;
            blobs?: undefined;
            blobVersionedHashes?: undefined;
            gasPrice?: undefined;
            maxFeePerBlobGas?: undefined;
            maxFeePerGas?: bigint;
            maxPriorityFeePerGas?: bigint;
            sidecars?: undefined;
        }) & {
            authorizationList: import("viem").TransactionSerializableEIP7702["authorizationList"];
        } ? "eip7702" : never) | (request["type"] extends string ? Extract<request["type"], string> : never)>) ? T_12 extends "eip7702" ? import("viem").TransactionRequestEIP7702 : never : never : never)>> & {
            chainId?: number | undefined;
        }, (request["parameters"] extends readonly import("viem").PrepareTransactionRequestParameterType[] ? request["parameters"][number] : "type" | "fees" | "gas" | "nonce" | "blobVersionedHashes" | "chainId") extends infer T_13 ? T_13 extends (request["parameters"] extends readonly import("viem").PrepareTransactionRequestParameterType[] ? request["parameters"][number] : "type" | "fees" | "gas" | "nonce" | "blobVersionedHashes" | "chainId") ? T_13 extends "fees" ? "gasPrice" | "maxFeePerGas" | "maxPriorityFeePerGas" : T_13 : never : never> & (unknown extends request["kzg"] ? {} : Pick<request, "kzg">) & {
            _capabilities?: {
                [x: string]: any;
            };
        } extends infer T ? { [K in keyof T]: T[K]; } : never>;
        readContract: <const abi extends import("abitype").Abi | readonly unknown[], functionName extends import("viem").ContractFunctionName<abi, "pure" | "view">, const args extends import("viem").ContractFunctionArgs<abi, "pure" | "view", functionName>>(args: import("viem").ReadContractParameters<abi, functionName, args>) => Promise<import("viem").ReadContractReturnType<abi, functionName, args>>;
        sendRawTransaction: (args: import("viem").SendRawTransactionParameters) => Promise<import("viem").SendRawTransactionReturnType>;
        sendRawTransactionSync: (args: import("viem").SendRawTransactionSyncParameters) => Promise<{
            blobGasPrice?: bigint | undefined;
            blobGasUsed?: bigint | undefined;
            blockHash: import("viem").Hash;
            blockNumber: bigint;
            blockTimestamp?: bigint | undefined;
            contractAddress: import("abitype").Address | null | undefined;
            cumulativeGasUsed: bigint;
            effectiveGasPrice: bigint;
            from: import("abitype").Address;
            gasUsed: bigint;
            logs: import("viem").Log<bigint, number, false>[];
            logsBloom: import("viem").Hex;
            root?: `0x${string}` | undefined;
            status: "success" | "reverted";
            to: import("abitype").Address | null;
            transactionHash: import("viem").Hash;
            transactionIndex: number;
            type: import("viem").TransactionType;
            l1GasPrice: bigint | null;
            l1GasUsed: bigint | null;
            l1Fee: bigint | null;
            l1FeeScalar: number | null;
        }>;
        simulate: <const calls extends readonly unknown[]>(args: import("viem").SimulateBlocksParameters<calls>) => Promise<import("viem").SimulateBlocksReturnType<calls>>;
        simulateBlocks: <const calls extends readonly unknown[]>(args: import("viem").SimulateBlocksParameters<calls>) => Promise<import("viem").SimulateBlocksReturnType<calls>>;
        simulateCalls: <const calls extends readonly unknown[]>(args: import("viem").SimulateCallsParameters<calls>) => Promise<import("viem").SimulateCallsReturnType<calls>>;
        simulateContract: <const abi extends import("abitype").Abi | readonly unknown[], functionName extends import("viem").ContractFunctionName<abi, "nonpayable" | "payable">, const args_1 extends import("viem").ContractFunctionArgs<abi, "nonpayable" | "payable", functionName>, chainOverride extends import("viem").Chain | undefined, accountOverride extends import("viem").Account | import("abitype").Address | undefined = undefined>(args: import("viem").SimulateContractParameters<abi, functionName, args_1, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, chainOverride, accountOverride>) => Promise<import("viem").SimulateContractReturnType<abi, functionName, args_1, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, import("viem").Account, chainOverride, accountOverride>>;
        verifyHash: (args: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>;
        verifyMessage: (args: import("viem").VerifyMessageActionParameters) => Promise<import("viem").VerifyMessageActionReturnType>;
        verifySiweMessage: (args: {
            blockNumber?: bigint | undefined;
            blockTag?: import("viem").BlockTag;
            address?: import("abitype").Address | undefined;
            nonce?: string | undefined;
            domain?: string | undefined;
            scheme?: string | undefined;
            time?: Date | undefined;
            message: string;
            signature: import("viem").Hex;
        }) => Promise<boolean>;
        verifyTypedData: (args: import("viem").VerifyTypedDataActionParameters) => Promise<import("viem").VerifyTypedDataActionReturnType>;
        uninstallFilter: (args: import("viem").UninstallFilterParameters) => Promise<import("viem").UninstallFilterReturnType>;
        waitForTransactionReceipt: (args: import("viem").WaitForTransactionReceiptParameters<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>) => Promise<{
            blobGasPrice?: bigint | undefined;
            blobGasUsed?: bigint | undefined;
            blockHash: import("viem").Hash;
            blockNumber: bigint;
            blockTimestamp?: bigint | undefined;
            contractAddress: import("abitype").Address | null | undefined;
            cumulativeGasUsed: bigint;
            effectiveGasPrice: bigint;
            from: import("abitype").Address;
            gasUsed: bigint;
            logs: import("viem").Log<bigint, number, false>[];
            logsBloom: import("viem").Hex;
            root?: `0x${string}` | undefined;
            status: "success" | "reverted";
            to: import("abitype").Address | null;
            transactionHash: import("viem").Hash;
            transactionIndex: number;
            type: import("viem").TransactionType;
            l1GasPrice: bigint | null;
            l1GasUsed: bigint | null;
            l1Fee: bigint | null;
            l1FeeScalar: number | null;
        }>;
        watchBlockNumber: (args: import("viem").WatchBlockNumberParameters) => import("viem").WatchBlockNumberReturnType;
        watchBlocks: <includeTransactions extends boolean = false, blockTag extends import("viem").BlockTag = "latest">(args: import("viem").WatchBlocksParameters<import("viem").HttpTransport<undefined, false>, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, includeTransactions, blockTag>) => import("viem").WatchBlocksReturnType;
        watchContractEvent: <const abi extends import("abitype").Abi | readonly unknown[], eventName extends import("viem").ContractEventName<abi>, strict extends boolean | undefined = undefined>(args: import("viem").WatchContractEventParameters<abi, eventName, strict, import("viem").HttpTransport<undefined, false>>) => import("viem").WatchContractEventReturnType;
        watchEvent: <const abiEvent extends import("abitype").AbiEvent | undefined = undefined, const abiEvents extends readonly import("abitype").AbiEvent[] | readonly unknown[] | undefined = abiEvent extends import("abitype").AbiEvent ? [abiEvent] : undefined, strict extends boolean | undefined = undefined>(args: import("viem").WatchEventParameters<abiEvent, abiEvents, strict, import("viem").HttpTransport<undefined, false>>) => import("viem").WatchEventReturnType;
        watchPendingTransactions: (args: import("viem").WatchPendingTransactionsParameters<import("viem").HttpTransport<undefined, false>>) => import("viem").WatchPendingTransactionsReturnType;
        extend: <const client extends {
            [x: string]: unknown;
            account?: undefined;
            batch?: undefined;
            cacheTime?: undefined;
            ccipRead?: undefined;
            chain?: undefined;
            dataSuffix?: undefined;
            experimental_blockTag?: undefined;
            key?: undefined;
            name?: undefined;
            pollingInterval?: undefined;
            request?: undefined;
            transport?: undefined;
            type?: undefined;
            uid?: undefined;
        } & import("viem").ExactPartial<Pick<import("viem").PublicActions<import("viem").HttpTransport<undefined, false>, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, {
            address: undefined;
            type: "json-rpc";
        }>, "prepareTransactionRequest" | "call" | "createContractEventFilter" | "createEventFilter" | "estimateContractGas" | "estimateGas" | "getBlock" | "getBlockNumber" | "getChainId" | "getContractEvents" | "getEnsText" | "getFilterChanges" | "getGasPrice" | "getLogs" | "getTransaction" | "getTransactionCount" | "getTransactionReceipt" | "readContract" | "sendRawTransaction" | "simulateContract" | "uninstallFilter" | "watchBlockNumber" | "watchContractEvent"> & Pick<import("viem").WalletActions<{
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, {
            address: undefined;
            type: "json-rpc";
        }>, "sendTransaction" | "writeContract">>>(fn: (client: import("viem").Client<import("viem").HttpTransport<undefined, false>, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, {
            address: undefined;
            type: "json-rpc";
        }, [{
            Method: "web3_clientVersion";
            Parameters?: undefined;
            ReturnType: string;
        }, {
            Method: "web3_sha3";
            Parameters: [data: import("viem").Hash];
            ReturnType: string;
        }, {
            Method: "net_listening";
            Parameters?: undefined;
            ReturnType: boolean;
        }, {
            Method: "net_peerCount";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "net_version";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_blobBaseFee";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_blockNumber";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_call";
            Parameters: readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier, stateOverrideSet: import("viem").RpcStateOverride] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier, stateOverrideSet: import("viem").RpcStateOverride, blockOverrides: import("viem").RpcBlockOverrides];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_createAccessList";
            Parameters: [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>] | [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: {
                accessList: import("viem").AccessList;
                gasUsed: import("viem").Quantity;
            };
        }, {
            Method: "eth_chainId";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_coinbase";
            Parameters?: undefined;
            ReturnType: import("abitype").Address;
        }, {
            Method: "eth_estimateGas";
            Parameters: [transaction: import("viem").RpcTransactionRequest] | [transaction: import("viem").RpcTransactionRequest, block: import("viem").RpcBlockNumber | import("viem").BlockTag] | [transaction: import("viem").RpcTransactionRequest, block: import("viem").RpcBlockNumber | import("viem").BlockTag, stateOverride: import("viem").RpcStateOverride];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_fillTransaction";
            Parameters: [transaction: import("viem").RpcTransactionRequest];
            ReturnType: {
                capabilities?: Record<string, unknown> | undefined;
                raw: import("viem").Hex;
                tx: import("viem").RpcTransaction;
            };
        }, {
            Method: "eth_feeHistory";
            Parameters: [blockCount: import("viem").Quantity, newestBlock: import("viem").RpcBlockNumber | import("viem").BlockTag, rewardPercentiles: number[] | undefined];
            ReturnType: import("viem").RpcFeeHistory;
        }, {
            Method: "eth_gasPrice";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBalance";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBlockByHash";
            Parameters: [hash: import("viem").Hash, includeTransactionObjects: boolean];
            ReturnType: import("viem").RpcBlock | null;
        }, {
            Method: "eth_getBlockByNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, includeTransactionObjects: boolean];
            ReturnType: import("viem").RpcBlock | null;
        }, {
            Method: "eth_getBlockTransactionCountByHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBlockTransactionCountByNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getCode";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_getFilterChanges";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: import("viem").RpcLog[] | import("viem").Hex[];
        }, {
            Method: "eth_getFilterLogs";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: import("viem").RpcLog[];
        }, {
            Method: "eth_getLogs";
            Parameters: [{
                address?: import("abitype").Address | import("abitype").Address[] | undefined;
                topics?: import("viem").LogTopic[] | undefined;
            } & ({
                fromBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                toBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                blockHash?: undefined;
            } | {
                fromBlock?: undefined;
                toBlock?: undefined;
                blockHash?: import("viem").Hash | undefined;
            })];
            ReturnType: import("viem").RpcLog[];
        }, {
            Method: "eth_getProof";
            Parameters: [address: import("abitype").Address, storageKeys: import("viem").Hash[], block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").RpcProof;
        }, {
            Method: "eth_getStorageAt";
            Parameters: [address: import("abitype").Address, index: import("viem").Quantity, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_getTransactionByBlockHashAndIndex";
            Parameters: [hash: import("viem").Hash, index: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionByBlockNumberAndIndex";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, index: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionByHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionBySenderAndNonce";
            Parameters: [sender: import("abitype").Address, nonce: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionCount";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getTransactionReceipt";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").RpcTransactionReceipt | null;
        }, {
            Method: "eth_getUncleByBlockHashAndIndex";
            Parameters: [hash: import("viem").Hash, index: import("viem").Quantity];
            ReturnType: import("viem").RpcUncle | null;
        }, {
            Method: "eth_getUncleByBlockNumberAndIndex";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, index: import("viem").Quantity];
            ReturnType: import("viem").RpcUncle | null;
        }, {
            Method: "eth_getUncleCountByBlockHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getUncleCountByBlockNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_maxPriorityFeePerGas";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newBlockFilter";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newFilter";
            Parameters: [filter: {
                fromBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                toBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                address?: import("abitype").Address | import("abitype").Address[] | undefined;
                topics?: import("viem").LogTopic[] | undefined;
            }];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newPendingTransactionFilter";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_protocolVersion";
            Parameters?: undefined;
            ReturnType: string;
        }, {
            Method: "eth_sendRawTransaction";
            Parameters: [signedTransaction: import("viem").Hex];
            ReturnType: import("viem").Hash;
        }, {
            Method: "eth_sendRawTransactionSync";
            Parameters: [signedTransaction: import("viem").Hex] | [signedTransaction: import("viem").Hex, timeout: number];
            ReturnType: import("viem").RpcTransactionReceipt;
        }, {
            Method: "eth_simulateV1";
            Parameters: [{
                blockStateCalls: readonly {
                    blockOverrides?: import("viem").RpcBlockOverrides | undefined;
                    calls?: readonly import("viem").ExactPartial<import("viem").RpcTransactionRequest>[] | undefined;
                    stateOverrides?: import("viem").RpcStateOverride | undefined;
                }[];
                returnFullTransactions?: boolean | undefined;
                traceTransfers?: boolean | undefined;
                validation?: boolean | undefined;
            }, import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: readonly (import("viem").RpcBlock & {
                calls: readonly {
                    error?: {
                        data?: import("viem").Hex | undefined;
                        code: number;
                        message: string;
                    } | undefined;
                    logs?: readonly import("viem").RpcLog[] | undefined;
                    gasUsed: import("viem").Hex;
                    returnData: import("viem").Hex;
                    status: import("viem").Hex;
                }[];
            })[];
        }, {
            Method: "eth_uninstallFilter";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: boolean;
        }, ...any[]], import("viem").PublicActions<import("viem").HttpTransport<undefined, false>, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>>) => client) => import("viem").Client<import("viem").HttpTransport<undefined, false>, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }, {
            address: undefined;
            type: "json-rpc";
        }, [{
            Method: "web3_clientVersion";
            Parameters?: undefined;
            ReturnType: string;
        }, {
            Method: "web3_sha3";
            Parameters: [data: import("viem").Hash];
            ReturnType: string;
        }, {
            Method: "net_listening";
            Parameters?: undefined;
            ReturnType: boolean;
        }, {
            Method: "net_peerCount";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "net_version";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_blobBaseFee";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_blockNumber";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_call";
            Parameters: readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier, stateOverrideSet: import("viem").RpcStateOverride] | readonly [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier, stateOverrideSet: import("viem").RpcStateOverride, blockOverrides: import("viem").RpcBlockOverrides];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_createAccessList";
            Parameters: [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>] | [transaction: import("viem").ExactPartial<import("viem").RpcTransactionRequest>, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: {
                accessList: import("viem").AccessList;
                gasUsed: import("viem").Quantity;
            };
        }, {
            Method: "eth_chainId";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_coinbase";
            Parameters?: undefined;
            ReturnType: import("abitype").Address;
        }, {
            Method: "eth_estimateGas";
            Parameters: [transaction: import("viem").RpcTransactionRequest] | [transaction: import("viem").RpcTransactionRequest, block: import("viem").RpcBlockNumber | import("viem").BlockTag] | [transaction: import("viem").RpcTransactionRequest, block: import("viem").RpcBlockNumber | import("viem").BlockTag, stateOverride: import("viem").RpcStateOverride];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_fillTransaction";
            Parameters: [transaction: import("viem").RpcTransactionRequest];
            ReturnType: {
                capabilities?: Record<string, unknown> | undefined;
                raw: import("viem").Hex;
                tx: import("viem").RpcTransaction;
            };
        }, {
            Method: "eth_feeHistory";
            Parameters: [blockCount: import("viem").Quantity, newestBlock: import("viem").RpcBlockNumber | import("viem").BlockTag, rewardPercentiles: number[] | undefined];
            ReturnType: import("viem").RpcFeeHistory;
        }, {
            Method: "eth_gasPrice";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBalance";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBlockByHash";
            Parameters: [hash: import("viem").Hash, includeTransactionObjects: boolean];
            ReturnType: import("viem").RpcBlock | null;
        }, {
            Method: "eth_getBlockByNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, includeTransactionObjects: boolean];
            ReturnType: import("viem").RpcBlock | null;
        }, {
            Method: "eth_getBlockTransactionCountByHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getBlockTransactionCountByNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getCode";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_getFilterChanges";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: import("viem").RpcLog[] | import("viem").Hex[];
        }, {
            Method: "eth_getFilterLogs";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: import("viem").RpcLog[];
        }, {
            Method: "eth_getLogs";
            Parameters: [{
                address?: import("abitype").Address | import("abitype").Address[] | undefined;
                topics?: import("viem").LogTopic[] | undefined;
            } & ({
                fromBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                toBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                blockHash?: undefined;
            } | {
                fromBlock?: undefined;
                toBlock?: undefined;
                blockHash?: import("viem").Hash | undefined;
            })];
            ReturnType: import("viem").RpcLog[];
        }, {
            Method: "eth_getProof";
            Parameters: [address: import("abitype").Address, storageKeys: import("viem").Hash[], block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").RpcProof;
        }, {
            Method: "eth_getStorageAt";
            Parameters: [address: import("abitype").Address, index: import("viem").Quantity, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Hex;
        }, {
            Method: "eth_getTransactionByBlockHashAndIndex";
            Parameters: [hash: import("viem").Hash, index: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionByBlockNumberAndIndex";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, index: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionByHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionBySenderAndNonce";
            Parameters: [sender: import("abitype").Address, nonce: import("viem").Quantity];
            ReturnType: import("viem").RpcTransaction | null;
        }, {
            Method: "eth_getTransactionCount";
            Parameters: [address: import("abitype").Address, block: import("viem").RpcBlockNumber | import("viem").BlockTag | import("viem").RpcBlockIdentifier];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getTransactionReceipt";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").RpcTransactionReceipt | null;
        }, {
            Method: "eth_getUncleByBlockHashAndIndex";
            Parameters: [hash: import("viem").Hash, index: import("viem").Quantity];
            ReturnType: import("viem").RpcUncle | null;
        }, {
            Method: "eth_getUncleByBlockNumberAndIndex";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag, index: import("viem").Quantity];
            ReturnType: import("viem").RpcUncle | null;
        }, {
            Method: "eth_getUncleCountByBlockHash";
            Parameters: [hash: import("viem").Hash];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_getUncleCountByBlockNumber";
            Parameters: [block: import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_maxPriorityFeePerGas";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newBlockFilter";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newFilter";
            Parameters: [filter: {
                fromBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                toBlock?: import("viem").RpcBlockNumber | import("viem").BlockTag | undefined;
                address?: import("abitype").Address | import("abitype").Address[] | undefined;
                topics?: import("viem").LogTopic[] | undefined;
            }];
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_newPendingTransactionFilter";
            Parameters?: undefined;
            ReturnType: import("viem").Quantity;
        }, {
            Method: "eth_protocolVersion";
            Parameters?: undefined;
            ReturnType: string;
        }, {
            Method: "eth_sendRawTransaction";
            Parameters: [signedTransaction: import("viem").Hex];
            ReturnType: import("viem").Hash;
        }, {
            Method: "eth_sendRawTransactionSync";
            Parameters: [signedTransaction: import("viem").Hex] | [signedTransaction: import("viem").Hex, timeout: number];
            ReturnType: import("viem").RpcTransactionReceipt;
        }, {
            Method: "eth_simulateV1";
            Parameters: [{
                blockStateCalls: readonly {
                    blockOverrides?: import("viem").RpcBlockOverrides | undefined;
                    calls?: readonly import("viem").ExactPartial<import("viem").RpcTransactionRequest>[] | undefined;
                    stateOverrides?: import("viem").RpcStateOverride | undefined;
                }[];
                returnFullTransactions?: boolean | undefined;
                traceTransfers?: boolean | undefined;
                validation?: boolean | undefined;
            }, import("viem").RpcBlockNumber | import("viem").BlockTag];
            ReturnType: readonly (import("viem").RpcBlock & {
                calls: readonly {
                    error?: {
                        data?: import("viem").Hex | undefined;
                        code: number;
                        message: string;
                    } | undefined;
                    logs?: readonly import("viem").RpcLog[] | undefined;
                    gasUsed: import("viem").Hex;
                    returnData: import("viem").Hex;
                    status: import("viem").Hex;
                }[];
            })[];
        }, {
            Method: "eth_uninstallFilter";
            Parameters: [filterId: import("viem").Quantity];
            ReturnType: boolean;
        }, ...any[]], { [K in keyof client]: client[K]; } & import("viem").PublicActions<import("viem").HttpTransport<undefined, false>, {
            blockExplorers: {
                readonly default: {
                    readonly name: "Basescan";
                    readonly url: "https://sepolia.basescan.org";
                    readonly apiUrl: "https://api-sepolia.basescan.org/api";
                };
            };
            blockTime: 2000;
            contracts: {
                readonly disputeGameFactory: {
                    readonly 11155111: {
                        readonly address: "0xd6E6dBf4F7EA0ac412fD8b65ED297e64BB7a06E1";
                    };
                };
                readonly l2OutputOracle: {
                    readonly 11155111: {
                        readonly address: "0x84457ca9D0163FbC4bbfe4Dfbb20ba46e48DF254";
                    };
                };
                readonly portal: {
                    readonly 11155111: {
                        readonly address: "0x49f53e41452c74589e85ca1677426ba426459e85";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly l1StandardBridge: {
                    readonly 11155111: {
                        readonly address: "0xfd0Bf71F60660E2f608ed56e1659C450eB113120";
                        readonly blockCreated: 4446677;
                    };
                };
                readonly multicall3: {
                    readonly address: "0xca11bde05977b3631167028862be2a173976ca11";
                    readonly blockCreated: 1059647;
                };
                readonly gasPriceOracle: {
                    readonly address: "0x420000000000000000000000000000000000000F";
                };
                readonly l1Block: {
                    readonly address: "0x4200000000000000000000000000000000000015";
                };
                readonly l2CrossDomainMessenger: {
                    readonly address: "0x4200000000000000000000000000000000000007";
                };
                readonly l2Erc721Bridge: {
                    readonly address: "0x4200000000000000000000000000000000000014";
                };
                readonly l2StandardBridge: {
                    readonly address: "0x4200000000000000000000000000000000000010";
                };
                readonly l2ToL1MessagePasser: {
                    readonly address: "0x4200000000000000000000000000000000000016";
                };
            };
            ensTlds?: readonly string[] | undefined;
            id: 84532;
            name: "Base Sepolia";
            nativeCurrency: {
                readonly name: "Sepolia Ether";
                readonly symbol: "ETH";
                readonly decimals: 18;
            };
            experimental_preconfirmationTime?: number | undefined | undefined;
            rpcUrls: {
                readonly default: {
                    readonly http: readonly ["https://sepolia.base.org"];
                };
            };
            sourceId: 11155111;
            testnet: true;
            custom?: Record<string, unknown> | undefined;
            extendSchema?: Record<string, unknown> | undefined;
            fees?: import("viem").ChainFees<undefined> | undefined;
            formatters: {
                readonly block: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcBlock, action?: string | undefined) => {
                        baseFeePerGas: bigint | null;
                        blobGasUsed: bigint;
                        difficulty: bigint;
                        excessBlobGas: bigint;
                        extraData: import("viem").Hex;
                        gasLimit: bigint;
                        gasUsed: bigint;
                        hash: `0x${string}` | null;
                        logsBloom: `0x${string}` | null;
                        miner: import("abitype").Address;
                        mixHash: import("viem").Hash;
                        nonce: `0x${string}` | null;
                        number: bigint | null;
                        parentBeaconBlockRoot?: `0x${string}` | undefined;
                        parentHash: import("viem").Hash;
                        receiptsRoot: import("viem").Hex;
                        sealFields: import("viem").Hex[];
                        sha3Uncles: import("viem").Hash;
                        size: bigint;
                        stateRoot: import("viem").Hash;
                        timestamp: bigint;
                        totalDifficulty: bigint | null;
                        transactions: `0x${string}`[] | import("viem/chains").OpStackTransaction<boolean>[];
                        transactionsRoot: import("viem").Hash;
                        uncles: import("viem").Hash[];
                        withdrawals?: import("viem").Withdrawal[] | undefined | undefined;
                        withdrawalsRoot?: `0x${string}` | undefined;
                    } & {};
                    type: "block";
                };
                readonly transaction: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransaction, action?: string | undefined) => ({
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: boolean;
                        mint?: bigint | undefined | undefined;
                        sourceHash: import("viem").Hex;
                        type: "deposit";
                    } | {
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        v: bigint;
                        to: import("abitype").Address | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        nonce: number;
                        value: bigint;
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        accessList?: undefined | undefined;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId?: number | undefined;
                        yParity?: undefined | undefined;
                        type: "legacy";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip2930";
                        gasPrice: bigint;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas?: undefined | undefined;
                        maxPriorityFeePerGas?: undefined | undefined;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip1559";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList?: undefined | undefined;
                        blobVersionedHashes: readonly import("viem").Hex[];
                        chainId: number;
                        type: "eip4844";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas: bigint;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    } | {
                        blockHash: `0x${string}` | null;
                        blockNumber: bigint | null;
                        from: import("abitype").Address;
                        gas: bigint;
                        hash: import("viem").Hash;
                        input: import("viem").Hex;
                        nonce: number;
                        r: import("viem").Hex;
                        s: import("viem").Hex;
                        to: import("abitype").Address | null;
                        transactionIndex: number | null;
                        typeHex: import("viem").Hex | null;
                        v: bigint;
                        value: bigint;
                        yParity: number;
                        accessList: import("viem").AccessList;
                        authorizationList: import("viem").SignedAuthorizationList;
                        blobVersionedHashes?: undefined | undefined;
                        chainId: number;
                        type: "eip7702";
                        gasPrice?: undefined | undefined;
                        maxFeePerBlobGas?: undefined | undefined;
                        maxFeePerGas: bigint;
                        maxPriorityFeePerGas: bigint;
                        isSystemTx?: undefined | undefined;
                        mint?: undefined | undefined;
                        sourceHash?: undefined | undefined;
                    }) & {};
                    type: "transaction";
                };
                readonly transactionReceipt: {
                    exclude: [] | undefined;
                    format: (args: import("viem/chains").OpStackRpcTransactionReceipt, action?: string | undefined) => {
                        blobGasPrice?: bigint | undefined;
                        blobGasUsed?: bigint | undefined;
                        blockHash: import("viem").Hash;
                        blockNumber: bigint;
                        blockTimestamp?: bigint | undefined;
                        contractAddress: import("abitype").Address | null | undefined;
                        cumulativeGasUsed: bigint;
                        effectiveGasPrice: bigint;
                        from: import("abitype").Address;
                        gasUsed: bigint;
                        logs: import("viem").Log<bigint, number, false>[];
                        logsBloom: import("viem").Hex;
                        root?: `0x${string}` | undefined;
                        status: "success" | "reverted";
                        to: import("abitype").Address | null;
                        transactionHash: import("viem").Hash;
                        transactionIndex: number;
                        type: import("viem").TransactionType;
                        l1GasPrice: bigint | null;
                        l1GasUsed: bigint | null;
                        l1Fee: bigint | null;
                        l1FeeScalar: number | null;
                    } & {};
                    type: "transactionReceipt";
                };
            };
            prepareTransactionRequest?: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | [fn: ((args: import("viem").PrepareTransactionRequestParameters, options: {
                phase: "beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters";
            }) => Promise<import("viem").PrepareTransactionRequestParameters>) | undefined, options: {
                runAt: readonly ("beforeFillTransaction" | "beforeFillParameters" | "afterFillParameters")[];
            }] | undefined;
            serializers: {
                readonly transaction: typeof import("viem/chains").serializeTransactionOpStack;
            };
            verifyHash?: ((client: import("viem").Client, parameters: import("viem").VerifyHashActionParameters) => Promise<import("viem").VerifyHashActionReturnType>) | undefined;
            readonly network: "base-sepolia";
        }>>;
    };
    /**
     * CTO LOGIC: Create a fresh session key and INSTALL it on-chain using the master signer.
     * This matches 'activateCard' in the test repo.
     */
    static createAndInstallSessionKey(masterSignerPrivateKey: `0x${string}`, spendLimitEth?: string): Promise<{
        setupHash: string;
        sessionKeyPK: string;
    }>;
    /**
     * CTO LOGIC: Execute a payment using the stored session key.
     * This matches 'sendCardPayment' in the test repo.
     */
    static executeWithSessionKey(smartAccountAddress: `0x${string}`, sessionKeyPK: `0x${string}`, to: `0x${string}`, value: bigint, spendLimitEth: string): Promise<string>;
}
