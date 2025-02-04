
-- CreateEnum
CREATE TYPE "message_type" AS ENUM ('text', 'image', 'video', 'audio');

-- CreateEnum
CREATE TYPE "participant_type" AS ENUM ('lead', 'member');

-- CreateEnum
CREATE TYPE "device_type" AS ENUM ('APPLE');

-- CreateEnum
CREATE TYPE "report_status" AS ENUM ('PENDING', 'RESOLVED');

-- CreateTable
CREATE TABLE "user" (
    "id" UUID DEFAULT gen_random_uuid(),
    "phone" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "username" TEXT NOT NULL,
    "password" TEXT NOT NULL,
    "first_name" TEXT NOT NULL DEFAULT '',
    "middle_name" TEXT DEFAULT '',
    "last_name" TEXT NOT NULL DEFAULT '',
    "is_active" BOOLEAN NOT NULL DEFAULT false,
    "is_reported" BOOLEAN NOT NULL DEFAULT false,
    "is_blocked" BOOLEAN NOT NULL DEFAULT false,
    "preferences" TEXT DEFAULT '',
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "user_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "device" (
    "id" UUID DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "device_id" TEXT NOT NULL,
    "device_token" TEXT NOT NULL,
    "type" "device_type" NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "device_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "access" (
    "id" UUID DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "device_id" UUID NOT NULL,
    "token" TEXT NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP(3),

    CONSTRAINT "access_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "user_verification" (
    "user_id" UUID NOT NULL,
    "verification_code" TEXT NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "user_verification_pkey" PRIMARY KEY ("user_id")
);

-- CreateTable
CREATE TABLE "user_contact" (
    "id" UUID DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "contact_id" UUID NOT NULL,
    "first_name" TEXT NOT NULL DEFAULT '',
    "last_name" TEXT NOT NULL DEFAULT '',
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "user_contact_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "contact" (
    "id" UUID DEFAULT gen_random_uuid(),
    "first_name" TEXT NOT NULL DEFAULT '',
    "middle_name" TEXT DEFAULT '',
    "last_name" TEXT NOT NULL DEFAULT '',
    "phone" TEXT NOT NULL,
    "email" TEXT NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "contact_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "block_list" (
    "id" UUID DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "participant_id" UUID NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "block_list_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "conversation" (
    "id" UUID DEFAULT gen_random_uuid(),
    "title" TEXT NOT NULL,
    "creator_id" UUID NOT NULL,
    "channel_id" UUID NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,
    "deleted_at" TIMESTAMP(3),

    CONSTRAINT "conversation_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "participant" (
    "id" UUID DEFAULT gen_random_uuid(),
    "conversation_id" UUID NOT NULL,
    "user_id" UUID NOT NULL,
    "type" "participant_type" NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "participant_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "message" (
    "id" UUID DEFAULT gen_random_uuid(),
    "guid" TEXT NOT NULL,
    "conversation_id" UUID NOT NULL,
    "sender_id" UUID NOT NULL,
    "message_type" "message_type" NOT NULL,
    "message" TEXT NOT NULL DEFAULT '',
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "deleted_at" TIMESTAMP(3),

    CONSTRAINT "message_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "attachment" (
    "id" UUID DEFAULT gen_random_uuid(),
    "message_id" UUID NOT NULL,
    "thumb_url" TEXT NOT NULL,
    "file_url" TEXT NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "attachment_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "deleted_message" (
    "id" UUID DEFAULT gen_random_uuid(),
    "message_id" UUID NOT NULL,
    "user_id" UUID NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "deleted_message_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "deleted_conversation" (
    "id" UUID DEFAULT gen_random_uuid(),
    "conversation_id" UUID NOT NULL,
    "user_id" UUID NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "deleted_conversation_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "report" (
    "id" UUID DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "participant_id" UUID NOT NULL,
    "report_type" TEXT NOT NULL,
    "notes" TEXT,
    "status" "report_status" NOT NULL DEFAULT 'PENDING',
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "report_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "activity" (
    "id" UUID DEFAULT gen_random_uuid(),
    "activity_type" TEXT NOT NULL,
    "activity_id" UUID NOT NULL,
    "title" TEXT NOT NULL,
    "detail" TEXT,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "activity_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "friend" (
    "id" UUID DEFAULT gen_random_uuid(),
    "requester_id" UUID NOT NULL,
    "receiver_id" UUID NOT NULL,
    "status" TEXT NOT NULL DEFAULT 'PENDING',
    "createdAt" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT "friend_pkey" PRIMARY KEY ("id")
);

-- CreateTable
CREATE TABLE "story" (
    "id" UUID DEFAULT gen_random_uuid(),
    "user_id" UUID NOT NULL,
    "content" TEXT NOT NULL,
    "created_at" TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at" TIMESTAMP(3) NOT NULL,
    "expires_at" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "story_pkey" PRIMARY KEY ("id")
);

-- CreateIndex
CREATE UNIQUE INDEX "user_phone_key" ON "user"("phone");

-- CreateIndex
CREATE UNIQUE INDEX "user_email_key" ON "user"("email");

-- CreateIndex
CREATE UNIQUE INDEX "user_contact_user_id_contact_id_key" ON "user_contact"("user_id", "contact_id");

-- CreateIndex
CREATE UNIQUE INDEX "participant_conversation_id_user_id_key" ON "participant"("conversation_id", "user_id");

-- CreateIndex
CREATE UNIQUE INDEX "friend_requester_id_receiver_id_key" ON "friend"("requester_id", "receiver_id");

-- AddForeignKey
ALTER TABLE "device" ADD CONSTRAINT "device_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "access" ADD CONSTRAINT "access_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "access" ADD CONSTRAINT "access_device_id_fkey" FOREIGN KEY ("device_id") REFERENCES "device"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "user_verification" ADD CONSTRAINT "user_verification_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "user_contact" ADD CONSTRAINT "user_contact_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "user_contact" ADD CONSTRAINT "user_contact_contact_id_fkey" FOREIGN KEY ("contact_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "block_list" ADD CONSTRAINT "block_list_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "participant" ADD CONSTRAINT "participant_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "participant" ADD CONSTRAINT "participant_conversation_id_fkey" FOREIGN KEY ("conversation_id") REFERENCES "conversation"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "message" ADD CONSTRAINT "message_conversation_id_fkey" FOREIGN KEY ("conversation_id") REFERENCES "conversation"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "message" ADD CONSTRAINT "message_sender_id_fkey" FOREIGN KEY ("sender_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "attachment" ADD CONSTRAINT "attachment_message_id_fkey" FOREIGN KEY ("message_id") REFERENCES "message"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "deleted_message" ADD CONSTRAINT "deleted_message_message_id_fkey" FOREIGN KEY ("message_id") REFERENCES "message"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "deleted_message" ADD CONSTRAINT "deleted_message_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "deleted_conversation" ADD CONSTRAINT "deleted_conversation_conversation_id_fkey" FOREIGN KEY ("conversation_id") REFERENCES "conversation"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "deleted_conversation" ADD CONSTRAINT "deleted_conversation_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "report" ADD CONSTRAINT "report_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "friend" ADD CONSTRAINT "friend_requester_id_fkey" FOREIGN KEY ("requester_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "friend" ADD CONSTRAINT "friend_receiver_id_fkey" FOREIGN KEY ("receiver_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "story" ADD CONSTRAINT "story_user_id_fkey" FOREIGN KEY ("user_id") REFERENCES "user"("id") ON DELETE RESTRICT ON UPDATE CASCADE;
