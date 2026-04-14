<p align="center">
  <img src="https://img.shields.io/badge/Tranzo-Self%20Custody%20Wallet-1D9E75?style=for-the-badge&logo=ethereum&logoColor=white" alt="Tranzo Badge"/>
</p>

<h1 align="center">Tranzo.money</h1>

<p align="center">
  <strong>Self-Custody Crypto Wallet • Gasless Transactions • Real-Time Salary Streaming</strong>
</p>

<p align="center">
  <a href="https://tranzo.money">Website</a> •
  <a href="#architecture">Architecture</a> •
  <a href="#tech-stack">Tech Stack</a> •
  <a href="#getting-started">Getting Started</a> •
  <a href="#api-reference">API Reference</a>
</p>

<p align="center">
  <img src="https://img.shields.io/github/actions/workflow/status/Pranav00x/Tranzo.money/android-build.yml?style=flat-square&label=Android%20Build&color=1D9E75" alt="Build Status"/>
  <img src="https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white" alt="Platform"/>
  <img src="https://img.shields.io/badge/Solidity-^0.8.24-363636?style=flat-square&logo=solidity&logoColor=white" alt="Solidity"/>
  <img src="https://img.shields.io/badge/Backend-Node.js%20%2B%20TypeScript-3178C6?style=flat-square&logo=typescript&logoColor=white" alt="Backend"/>
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=flat-square" alt="License"/>
</p>

---

## What is Tranzo?

**Tranzo** is a self-custody crypto wallet ecosystem where users hold their own keys, send/receive/swap crypto, receive salary streams via **Dripper**, and interact with DeFi — all through **ERC-4337 smart accounts** with completely gasless transactions.

### Core Thesis

> **Your keys, your crypto.** No custodians. Smart accounts with programmable spending limits, social recovery, session keys, and gas abstraction — all invisible to the user.

### Why Tranzo?

| Problem | Tranzo's Solution |
|---------|-------------------|
| Gas fees are confusing and expensive | **Gasless transactions** — Openfort-sponsored UserOps |
| Salary payments are monthly, delayed | **Real-time streaming** — earn every second via Dripper |
| Custodial wallets own your keys | **100% self-custody** — ERC-4337 smart accounts |
| Crypto apps feel like developer tools | **CheQ-inspired UI** — clean, modern, card-based |
| Key loss means fund loss | **Social recovery** — guardian-based account recovery |

---

## Product Features

### 💼 Smart Wallet
- **ERC-4337 Smart Accounts** managed by [Openfort](https://openfort.xyz) — counterfactual deployment, session keys, batch transactions
- **Multi-chain support** — Polygon + Base (more chains planned)
- **Gasless everything** — all transactions are sponsored via bundler/paymaster

### 💧 Dripper (Salary Streaming)
- **Linear vesting salary streams** — employers deposit once, employees earn every second
- **Real-time accrual counter** — watch your balance tick up in real-time
- **Withdraw anytime** — pull your earned funds whenever you want
- **On-chain contract** — `TranzoDripper.sol` handles all streaming logic

### 💸 Send / Receive / Swap
- **Send tokens** with QR scan or paste — review screen with fee breakdown
- **Receive** with auto-generated QR code + network selector (Polygon/Base)
- **Swap** between tokens with live exchange rates and slippage control

### 🔒 Security
- **Hardware-backed Keystore** (Android Keystore + StrongBox)
- **Biometric gating** — fingerprint/face unlock for app + transactions
- **Social recovery** — add guardians to recover your account
- **Session management** — view and revoke active sessions

### 📊 Activity
- **Full transaction history** with filterable tabs (All/Sent/Received/Swap/Dripper)
- **Real-time status tracking** via Openfort UserOp monitorin## 🏗️ Architecture

```mermaid
graph TD
    subgraph "📱 Mobile Application (Android)"
        A[Jetpack Compose UI]
        B[Hilt DI]
        C[Retrofit / OkHttp]
        D[Room / DataStore]
    end

    subgraph "🖥️ Backend (Node.js/TS)"
        E[Express.js API]
        F[Auth Service]
        G[Openfort SDK]
        H[Prisma ORM]
    end

    subgraph "⛓️ Blockchain (Polygon/Base)"
        I[ERC-4337 Smart Account]
        J[TranzoDripper.sol]
        K[EntryPoint Contract]
    end

    subgraph "🛠️ Infrastructure"
        L[Openfort Bundler]
        M[Openfort Paymaster]
        N[PostgreSQL]
    end

    A <--> B
    B <--> C
    C <--> E
    E <--> F
    E <--> G
    G <--> L
    L <--> K
    K <--> I
    E <--> H
    H <--> N
    I <--> J
```

### 🔁 Data Flow

```mermaid
sequenceDiagram
    participant U as User (Android)
    participant B as Backend
    participant O as Openfort
    participant C as Chain (Polygon)

    U->>B: POST /transfers/send
    B->>O: Create Transaction Intent
    O-->>B: UserOperation Hash
    B->>O: Sign & Send UserOp
    O->>C: Submit to Bundler
    C->>C: Execute on EntryPoint
    C-->>O: Transaction Confirmed
    O-->>B: Webhook / Result
    B-->>U: 200 OK (Success)
```

### ⛽ ERC-4337 UserOp Flow

```mermaid
graph LR
    User[User / App] -->|Signs UserOp| Intent[Transaction Intent]
    Intent --> |Submits to| Bundler[Openfort Bundler]
    Bundler --> |Requests Gas from| Paymaster[Openfort Paymaster]
    Paymaster --> |Sponsors| EntryPoint[EntryPoint Contract]
    EntryPoint --> |Validates & Executes| SmartAccount[User Smart Account]
    SmartAccount --> |Calls| Contract[Target Contract / Transfer]
```

---

## 🚀 Tech Stack

### 📱 Android App

| Technology | Purpose |
|-----------|---------|
| **Kotlin** | Language |
| **Jetpack Compose** | Declarative UI framework |
| **Material 3** | Design system with CheQ-inspired customization |
| **Hilt (Dagger)** | Dependency injection |
| **Retrofit + OkHttp** | HTTP client with auth interceptor |
| **Room** | Local database for caching |
| **Compose Navigation** | Type-safe navigation with NavHost |
| **Coroutines + Flow** | Async operations + reactive state |
| **ZXing** | QR code generation/scanning |
| **Lottie** | Micro-animations |
| **Coil** | Image loading |
| **DataStore** | Preferences storage |  
| **BiometricPrompt** | Fingerprint/Face gating |
| **Credential Manager** | Google One Tap sign-in |

### 🖥️ Backend

| Technology | Purpose |
|-----------|---------|
| **Node.js + TypeScript** | Runtime + language |
| **Express.js** | HTTP framework |
| **Openfort SDK** | ERC-4337 account abstraction — player management, transaction intents, smart account lifecycle |
| **Prisma 6** | ORM with PostgreSQL |
| **JWT (jsonwebtoken)** | Authentication tokens |
| **Zod** | Runtime environment validation |
| **viem** | On-chain balance reads (native + ERC-20) |
| **Nodemailer** | OTP + welcome emails |
| **express-rate-limit** | Three-tier rate limiting (general / auth / sensitive) |
| **bcrypt** | OTP hashing |

### ⛓️ Smart Contracts

| Technology | Purpose |
|-----------|---------|
| **Solidity ^0.8.24** | Language |
| **Foundry (Forge)** | Build, test, deploy |
| **OpenZeppelin** | Contract utilities (IERC20, SafeERC20) |

### 🔧 Infrastructure

| Technology | Purpose |
|-----------|---------|
| **GitHub Actions** | CI/CD — builds APK, sends to Telegram |
| **Openfort** | Managed ERC-4337 infrastructure (bundler, paymaster, smart accounts) |
| **Polygon** | Primary L2 chain |
| **Base** | Secondary L2 chain |
| **PostgreSQL** | Primary database |

---

## 📂 Repository Structure

```
Tranzo.money/
├── .github/
│   └── workflows/
│       └── android-build.yml       # CI: Build APK → Telegram
│
├── android/                         # 📱 Android Application
│   ├── app/
│   │   ├── build.gradle.kts        # Dependencies + build config
│   │   ├── proguard-rules.pro
│   │   └── src/main/
│   │       ├── AndroidManifest.xml
│   │       ├── res/
│   │       │   └── values/
│   │       │       ├── strings.xml
│   │       │       └── themes.xml
│   │       └── java/com/tranzo/app/
│   │           ├── MainActivity.kt          # Navigation graph
│   │           ├── TranzoApplication.kt     # Hilt app
│   │           ├── data/
│   │           │   ├── api/TranzoApi.kt     # Retrofit interface
│   │           │   └── model/ApiModels.kt   # DTOs
│   │           ├── di/
│   │           │   └── NetworkModule.kt     # Hilt DI
│   │           └── ui/
│   │               ├── auth/
│   │               │   ├── AuthViewModel.kt
│   │               │   ├── WelcomeScreen.kt
│   │               │   ├── OtpScreen.kt
│   │               │   └── WalletCreationScreen.kt
│   │               ├── home/
│   │               │   ├── HomeScreen.kt
│   │               │   └── HomeViewModel.kt
│   │               ├── send/
│   │               │   ├── SendScreen.kt
│   │               │   ├── SendConfirmationScreen.kt
│   │               │   └── SendViewModel.kt
│   │               ├── receive/
│   │               │   └── ReceiveScreen.kt
│   │               ├── swap/
│   │               │   └── SwapScreen.kt
│   │               ├── dripper/
│   │               │   ├── DripperDashboardScreen.kt
│   │               │   ├── StreamDetailScreen.kt
│   │               │   ├── CreateStreamScreen.kt
│   │               │   └── DripperViewModel.kt
│   │               ├── history/
│   │               │   ├── TransactionHistoryScreen.kt
│   │               │   └── HistoryViewModel.kt
│   │               ├── settings/
│   │               │   └── SettingsScreen.kt
│   │               ├── security/
│   │               │   └── SecurityScreen.kt
│   │               ├── splash/
│   │               │   └── SplashScreen.kt
│   │               ├── onboarding/
│   │               │   └── OnboardingScreen.kt
│   │               ├── navigation/
│   │               │   ├── Screen.kt
│   │               │   └── BottomNavBar.kt
│   │               ├── components/
│   │               │   └── TranzoComponents.kt
│   │               └── theme/
│   │                   ├── Color.kt
│   │                   ├── Type.kt
│   │                   ├── Shape.kt
│   │                   └── Theme.kt
│   ├── build.gradle.kts             # Root build file
│   ├── settings.gradle.kts
│   ├── gradle.properties
│   └── gradle/
│       ├── libs.versions.toml       # Version catalog
│       └── wrapper/
│
├── backend/                          # 🖥️ Node.js Backend
│   ├── package.json
│   ├── tsconfig.json
│   ├── .env.example
│   ├── prisma/
│   │   └── schema.prisma            # Database schema
│   └── src/
│       ├── index.ts                  # Server entry
│       ├── config/
│       │   ├── env.ts                # Zod-validated config
│       │   └── openfort.ts           # SDK singleton
│       ├── middleware/
│       │   ├── auth.ts               # JWT verification
│       │   └── rateLimit.ts          # Three-tier limiting
│       ├── routes/
│       │   ├── auth.routes.ts        # Email/OTP + Google
│       │   ├── balances.routes.ts    # Token balances
│       │   ├── transfers.routes.ts   # Send + history
│       │   ├── dripper.routes.ts     # Salary streams
│       │   └── settings.routes.ts    # Profile
│       ├── services/
│       │   ├── auth.service.ts       # Auth logic
│       │   ├── openfort.service.ts   # ERC-4337 wrapper
│       │   ├── balance.service.ts    # On-chain reads
│       │   ├── stream.service.ts     # Dripper logic
│       │   ├── email.service.ts      # OTP emails
│       │   └── prisma.service.ts     # DB client
│       └── utils/
│           └── errors.ts             # Error handling
│
├── smart_contracts/                   # ⛓️ Solidity
│   ├── foundry.toml
│   ├── src/
│   │   └── TranzoDripper.sol         # Salary streaming
│   ├── test/
│   │   └── TranzoDripper.t.sol       # 8 test cases
│   └── script/
│       └── Deploy.s.sol              # Deployment
│
├── .gitignore
├── LICENSE
└── README.md                         # ← You are here
```

---

## 🛠️ Getting Started

### Prerequisites

| Tool | Version | Purpose |
|------|---------|---------|
| Node.js | ≥ 18 | Backend runtime |
| PostgreSQL | ≥ 14 | Database |
| Android Studio | Hedgehog+ | Android development |
| JDK | 17 | Android builds |
| Foundry | Latest | Smart contract dev |

### 1. Clone

```bash
git clone https://github.com/Pranav00x/Tranzo.money.git
cd Tranzo.money
```

### 2. Backend Setup

```bash
cd backend
cp .env.example .env
# Fill in your values: Openfort keys, DB URL, SMTP, JWT secret

npm install
npx prisma generate
npx prisma db push
npm run dev
```

#### Environment Variables

```env
# Openfort (https://dashboard.openfort.xyz)
OPENFORT_API_KEY=sk_live_...
OPENFORT_SECRET_KEY=sk_live_...
OPENFORT_POLICY_ID=pol_...

# Database
DATABASE_URL=postgresql://user:pass@localhost:5432/tranzo

# JWT
JWT_SECRET=your-256-bit-secret
JWT_EXPIRES_IN=15m
REFRESH_TOKEN_EXPIRES_IN=7d

# Email (SMTP or Resend)
SMTP_HOST=smtp.resend.com
SMTP_PORT=465
SMTP_USER=resend
SMTP_PASS=re_...
SMTP_FROM=noreply@tranzo.money

# Chains
POLYGON_RPC_URL=https://polygon-rpc.com
BASE_RPC_URL=https://mainnet.base.org

# Dripper Contract
DRIPPER_CONTRACT_ADDRESS=0x...
```

### 3. Smart Contracts

```bash
cd smart_contracts
forge install
forge build
forge test -vvv

# Deploy
forge script script/Deploy.s.sol \
  --rpc-url $POLYGON_RPC_URL \
  --broadcast \
  --verify
```

### 4. Android App

```bash
cd android
# Open in Android Studio, or:
./gradlew assembleDebug
```

The debug APK will be at `app/build/outputs/apk/debug/app-debug.apk`.

---

## 🔌 API Reference

### Authentication

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/auth/email` | Send OTP to email |
| `POST` | `/auth/verify-otp` | Verify OTP → get tokens |
| `POST` | `/auth/google` | Google OAuth login |
| `POST` | `/auth/refresh` | Refresh access token |
| `GET` | `/auth/me` | Get current user |
| `POST` | `/auth/logout` | Invalidate refresh token |

### Balances

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/balances` | Get token balances |
| `GET` | `/balances/tokens` | List supported tokens |

### Transfers

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/transfers/send` | Send tokens (Openfort intent) |
| `GET` | `/transfers/history` | Transaction history |
| `GET` | `/transfers/status/:id` | UserOp status |

### Dripper (Salary Streaming)

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/dripper` | Create salary stream |
| `GET` | `/dripper` | List streams by role |
| `GET` | `/dripper/:id` | Stream details |
| `POST` | `/dripper/:id/withdraw` | Withdraw earned funds |
| `POST` | `/dripper/:id/cancel` | Cancel stream + refund |

### Settings

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/user/profile` | Get profile |
| `PUT` | `/user/profile` | Update profile |
| `GET` | `/user/account` | Smart account info |

---

## 💎 Smart Contract: TranzoDripper

The only custom on-chain contract. All account abstraction (smart accounts, bundler, paymaster) is handled by **Openfort**.

```solidity
// Create a salary stream
function createStream(
    address employee,
    address token,
    uint256 totalAmount,
    uint256 startTime,
    uint256 endTime
) external returns (uint256 streamId);

// Check accrued balance (linear vesting)
function balanceOf(uint256 streamId) external view returns (uint256);

// Employee withdraws earned funds
function withdraw(uint256 streamId) external;

// Employer cancels — employee gets earned, employer gets refund
function cancel(uint256 streamId) external;
```

### Test Coverage

| Test | Description |
|------|-------------|
| `test_createStream` | Creates stream, verifies token transfer |
| `test_balanceOf_accrues` | Linear vesting at 50% and 100% |
| `test_withdraw` | Employee withdraws accrued funds |
| `test_cancel_refunds_employer` | Cancel splits funds correctly |
| `test_revert_cancel_unauthorized` | Only employer can cancel |
| `test_revert_invalid_time_range` | Rejects end < start |
| `test_revert_zero_amount` | Rejects zero deposit |
| `test_multiple_withdrawals` | Multiple withdrawals over time |

---

## 🎨 Design System

Tranzo's UI is inspired by **CheQ** — a modern fintech app known for its clean, premium feel.

### 🎨 Color Palette

| Token | Hex | Usage |
|-------|-----|-------|
| Primary Green | `#1D9E75` | CTAs, active states, accents |
| Light Teal | `#5DCAA5` | Secondary highlights |
| Navy | `#0B1D2E` | Dark gradient headers |
| Dark Teal | `#0F3D3E` | Gradient mid-point |
| Background | `#F5F6FA` | Screen backgrounds |
| Card Surface | `#FFFFFF` | Cards, inputs |
| Pale Teal | `#E8F8F2` | Icon backgrounds, badges |
| Text Primary | `#1A1A2E` | Headlines |
| Text Secondary | `#6B7280` | Body text |
| Text Tertiary | `#9CA3AF` | Captions, timestamps |
| Error | `#EF4444` | Error states |
| Warning | `#F59E0B` | Pending states |

### 🔡 Typography

- **Font**: System default (DM Sans when added)
- **Scale**: Display (36sp) → Headline (26/20/18sp) → Body (16/14/12sp) → Label (16/12/11sp)

### 📐 Shape Tokens

- Cards: `16dp` rounded
- Bottom sheets: `24dp` top corners
- Buttons: `28dp` pill shape
- Inputs: `12dp` rounded

---

## 📱 User Interface Flow

```mermaid
stateDiagram-v2
    [*] --> Splash
    Splash --> Onboarding
    Onboarding --> Welcome
    Welcome --> OTP
    OTP --> WalletCreation
    WalletCreation --> Home

    state Home {
        [*] --> Dashboard
        Dashboard --> Send
        Dashboard --> Receive
        Dashboard --> Swap
        Dashboard --> Dripper
        Dashboard --> History
        Dashboard --> Settings
    }

    Send --> SendConfirmation
    SendConfirmation --> Success
    Success --> Dashboard

    Dripper --> StreamDetail
    Dripper --> CreateStream
    StreamDetail --> Dashboard
    CreateStream --> Dashboard
```

---

## 🔄 CI/CD Pipeline

### GitHub Actions Workflow

On every push to `main`:

1. **Checkout** → **JDK 17** → **Gradle Wrapper** → **Cache**
2. `./gradlew assembleDebug`
3. Upload APK as GitHub artifact (14-day retention)
4. Send APK to **Telegram** with build info

```yaml
# Telegram delivery includes:
# - APK file attachment
# - Commit SHA
# - APK size
# - Branch name
# - Author name
# - Commit message
```

### Secrets Required

| Secret | Description |
|--------|-------------|
| `TG_BOT_TOKEN` | Telegram bot API token |
| `TG_CHAT_ID` | Telegram chat/group ID |

---

## 🗄️ Database Schema

```mermaid
erDiagram
    USER ||--o{ SOCIAL_ACCOUNT : has
    USER ||--o{ PASSKEY : has
    USER ||--o{ REFRESH_TOKEN : has
    USER ||--o{ OTP_CODE : requested
    USER ||--o{ SALARY_STREAM : manages
    USER ||--o{ USER_OP_LOG : records
    USER ||--o{ GAS_SPONSORSHIP : receives

    USER {
        string id PK
        string email
        string phone
        string displayName
        string smartAccount
        string openfortId
        string kycStatus
        datetime createdAt
    }

    SOCIAL_ACCOUNT {
        string provider
        string providerUid
        string userId FK
    }

    PASSKEY {
        string credentialId PK
        string publicKey
        string userId FK
    }

    SALARY_STREAM {
        string id PK
        string onChainId
        string employerId FK
        string employeeAddress
        string tokenAddress
        string amountPerSecond
        datetime startTime
        datetime endTime
        string status
    }

    USER_OP_LOG {
        string intentId PK
        string txHash
        string status
        string userId FK
    }
```
```

---

## 🔒 Security Model

### Non-Custodial by Design

- **No private keys on our servers** — all signing happens client-side or via Openfort's managed keys
- **ERC-4337 smart accounts** — recoverable by design (not EOA)
- **Openfort handles key management** — HSM-backed, SOC2 compliant

### Authentication Layers

1. **Email OTP** — 6-digit code, bcrypt-hashed, 10-min expiry
2. **Google OAuth** — Credential Manager integration
3. **JWT Access Tokens** — 15-min expiry, bearer auth
4. **Rotating Refresh Tokens** — family-based reuse detection
5. **Biometric Lock** — Android Keystore + BiometricPrompt

### Rate Limiting

| Tier | Limit | Endpoints |
|------|-------|-----------|
| General | 100 req/15min | All routes |
| Auth | 10 req/15min | Login, OTP, register |
| Sensitive | 5 req/15min | Transfers, stream creation |

---

## Supported Tokens

### Polygon

| Token | Address |
|-------|---------|
| USDC | `0x3c499c542cEF5E3811e1192ce70d8cC03d5c3359` |
| USDT | `0xc2132D05D31c914a87C6611C10748AEb04B58e8F` |
| WETH | `0x7ceB23fD6bC0adD59E62ac25578270cFf1b9f619` |
| POL | Native |

### Base

| Token | Address |
|-------|---------|
| USDC | `0x833589fCD6eDb6E08f4c7C32D4f71b54bdA02913` |
| WETH | `0x4200000000000000000000000000000000000006` |
| ETH | Native |

---

## Roadmap

- [x] **Phase 0** — Monorepo setup, Foundry + Node + Android
- [x] **Phase 1** — TranzoDripper contract + tests + deploy script
- [x] **Phase 2** — Backend: Auth, Openfort, Balances, Transfers, Dripper, Settings
- [x] **Phase 3** — Android: Auth flow + Home dashboard + Design system
- [x] **Phase 4** — Android: Send, Receive, Swap, Transaction History
- [x] **Phase 5** — Android: Dripper Dashboard, Stream Detail, Create Stream
- [x] **Phase 6** — CI/CD: GitHub Actions → APK → Telegram
- [ ] **Phase 7** — ViewModel ↔ API integration testing
- [ ] **Phase 8** — Biometric gating + Android Keystore
- [ ] **Phase 9** — Dark mode, confetti, haptic feedback
- [ ] **Phase 10** — Card issuance (Kulipa/Reap integration)
- [ ] **Phase 11** — Production deployment + TestFlight/Play Store

---

## Contributing

```bash
# Fork → Branch → Commit → PR
git checkout -b feat/amazing-feature
git commit -m "feat: add amazing feature"
git push origin feat/amazing-feature
```

Please follow [Conventional Commits](https://www.conventionalcommits.org/) for commit messages.

---

## Contact

| Channel | Contact |
|---------|---------|
| 🌐 Website | [tranzo.money](https://tranzo.money) |
| 📧 General | pranav@tranzo.money |
| 🐛 Bugs | bugs@tranzo.money |
| 🤝 Partnerships | partnerships@tranzo.money |
| 👨‍💻 Founder | [Pranav](https://github.com/Pranav00x) |

---

<p align="center">
  Built with 💚 by <a href="https://github.com/Pranav00x">Pranav</a>
</p>

<p align="center">
  <sub>© 2026 Tranzo.money — All rights reserved</sub>
</p>
