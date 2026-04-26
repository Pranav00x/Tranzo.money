-- Rename openfortPlayer to kernelAccountAddress
ALTER TABLE "User" RENAME COLUMN "openfortPlayer" TO "kernelAccountAddress";

-- Rename the unique constraint
ALTER TABLE "User" RENAME CONSTRAINT "User_openfortPlayer_key" TO "User_kernelAccountAddress_key";
