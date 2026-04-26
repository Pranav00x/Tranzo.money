# package.json Dependencies to Add

## Install Commands

```bash
# All-in-one install
npm install @simplewebauthn/server multer zod uuid
npm install --save-dev @types/multer @types/express

# Or individually:
npm install @simplewebauthn/server  # WebAuthn credential verification
npm install multer                   # File upload handling
npm install zod                      # Request validation
npm install uuid                     # Unique ID generation
npm install --save-dev @types/multer # TypeScript types
npm install --save-dev @types/express # TypeScript types (if not already present)
```

## What Gets Added to package.json

After running the install commands, your `package.json` should have these new entries:

### Dependencies (`dependencies` section)

```json
{
  "@simplewebauthn/server": "^10.0.0",
  "multer": "^1.4.5-lts.1",
  "zod": "^3.22.4",
  "uuid": "^9.0.1"
}
```

### DevDependencies (`devDependencies` section)

```json
{
  "@types/multer": "^1.4.11",
  "@types/express": "^4.17.21"
}
```

## Why Each Package?

| Package | Version | Purpose |
|---------|---------|---------|
| `@simplewebauthn/server` | ^10.0.0 | WebAuthn credential registration and authentication verification |
| `multer` | ^1.4.5 | Handle multipart/form-data for file uploads (avatars) |
| `zod` | ^3.22.4 | Runtime request validation and schema definition |
| `uuid` | ^9.0.1 | Generate unique identifiers (if not already using) |
| `@types/multer` | ^1.4.11 | TypeScript type definitions for multer |
| `@types/express` | ^4.17.21 | TypeScript type definitions for express (usually already present) |

## Verification

After installation, verify packages are installed:

```bash
npm list @simplewebauthn/server multer zod uuid
```

You should see:
```
tranzo-backend@1.0.0 /path/to/backend
├── @simplewebauthn/server@10.0.0
├── multer@1.4.5-lts.1
├── uuid@9.0.1
└── zod@3.22.4
```

## If You Already Have These

Some packages might already be in your project:

- **zod**: Likely already installed (used for validation)
- **uuid**: May already be installed
- **@types/express**: Probably already in devDependencies

You can check with:
```bash
npm list zod
npm list uuid
npm list @types/express
```

**If they're already there:** You only need to install:
```bash
npm install @simplewebauthn/server multer
npm install --save-dev @types/multer
```

## Optional but Recommended

For production, also consider:

```bash
# For Redis session storage (instead of in-memory Maps)
npm install redis

# For rate limiting
npm install express-rate-limit

# For file validation (virus scanning on uploads)
npm install multer-virus-scanner

# For cloud storage integration (instead of local files)
npm install aws-sdk  # For AWS S3
# OR
npm install cloudinary  # For Cloudinary

# For security headers
npm install helmet

# For CORS if needed
npm install cors
```

## Example package.json (Relevant Sections)

```json
{
  "name": "tranzo-backend",
  "version": "1.0.0",
  "type": "module",
  "scripts": {
    "dev": "tsx watch src/index.ts",
    "build": "tsc",
    "start": "node dist/index.js",
    "prisma:migrate": "prisma migrate dev",
    "prisma:studio": "prisma studio"
  },
  "dependencies": {
    "@simplewebauthn/server": "^10.0.0",
    "express": "^4.18.2",
    "multer": "^1.4.5-lts.1",
    "zod": "^3.22.4",
    "uuid": "^9.0.1",
    "prisma": "^5.0.0",
    "@prisma/client": "^5.0.0",
    "dotenv": "^16.0.3",
    "jsonwebtoken": "^9.1.2"
  },
  "devDependencies": {
    "@types/express": "^4.17.21",
    "@types/node": "^20.0.0",
    "@types/multer": "^1.4.11",
    "typescript": "^5.0.0",
    "tsx": "^4.0.0"
  }
}
```

## Troubleshooting

### `npm install` fails for @simplewebauthn/server

**Issue:** Build errors with native modules
**Solution:**
```bash
# Clear cache and retry
npm cache clean --force
npm install @simplewebauthn/server

# Or use yarn
yarn add @simplewebauthn/server
```

### Module not found errors after install

**Issue:** TypeScript can't find types
**Solution:**
```bash
# Reinstall types
npm install --save-dev @types/multer
npm install --save-dev @types/express

# Regenerate types
npm run build
```

### Multer types conflicts

**Issue:** Conflicts with existing multer types
**Solution:**
```bash
# Clear and reinstall
npm uninstall @types/multer
npm install --save-dev @types/multer@1.4.11
```

## Import Examples for These Packages

In your route files, you'll import like this:

```typescript
// WebAuthn server
import {
  generateRegistrationOptions,
  verifyRegistrationResponse,
  generateAuthenticationOptions,
  verifyAuthenticationResponse,
} from '@simplewebauthn/server';

// File uploads
import multer from 'multer';

// Validation
import { z } from 'zod';

// IDs
import { v4 as uuidv4 } from 'uuid';
```

These are all already done in the provided route files, so you just need to ensure the packages are installed!
