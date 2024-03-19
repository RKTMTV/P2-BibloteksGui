//SQL tabler

Table for Books
CREATE TABLE "Books" (
    "Book_id" INTEGER NOT NULL,
    "Book" text NOT NULL,
    "Writer" text NOT NULL,
    "Year_id" INTEGER,
    "Status" INTEGER NOT NULL,
    "User_id" INTEGER,
    PRIMARY KEY("Book_id")
);


Table for Users
CREATE TABLE "Users" (
    "User_id" INTEGER NOT NULL,
    "Name" TEXT,
    "Username" TEXT UNIQUE,
    "Pass" TEXT NOT NULL,
    "Add" TEXT,
    "City" TEXT,
    "Postcode" INTEGER,
    PRIMARY KEY("User_id")
);