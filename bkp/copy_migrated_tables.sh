#!/bin/sh
# Workbench Table Data copy script
# Workbench Version: 6.3.10
# 
# Execute this to copy table data from a source RDBMS to MySQL.
# Edit the options below to customize it. You will need to provide passwords, at least.
# 
# Source DB: Mysql@localhost:3306 (MySQL)
# Target DB: Mysql@localhost:3306


# Source and target DB passwords
arg_source_password=
arg_target_password=

if [ -z "$arg_source_password" ] && [ -z "$arg_target_password" ] ; then
    echo WARNING: Both source and target RDBMSes passwords are empty. You should edit this file to set them.
fi
arg_worker_count=2
# Uncomment the following options according to your needs

# Whether target tables should be truncated before copy
# arg_truncate_target=--truncate-target
# Enable debugging output
# arg_debug_output=--log-level=debug3

/Applications/MySQLWorkbench.app/Contents/MacOS/wbcopytables \
 --mysql-source="root@localhost:3306" \
 --target="root@localhost:3306" \
 --source-password="$arg_source_password" \
 --target-password="$arg_target_password" \
 --thread-count=$arg_worker_count \
 $arg_truncate_target \
 $arg_debug_output \
 --table '`NoteShelf_DB`' '`NS_USER_PROFILE`' '`Note_shelf_DB`' '`NS_USER_PROFILE`' '`USER_PROFILE_ID`' '`USER_PROFILE_ID`' '`USER_PROFILE_ID`, `USER_ID`, `GENDER`, `PROFILE_IMAGE`, `WORK`, `CONTACT_NUMBER`, `BIRTH_DATE`, `BIRTH_YEAR`, `LANGUAGE`, `CREATED_DATE`, `UPDATED_DATE`' --table '`NoteShelf_DB`' '`NS_USER`' '`Note_shelf_DB`' '`NS_USER`' '`USER_ID`' '`USER_ID`' '`USER_ID`, `USER_NAME`, `FIRST_NAME`, `LAST_NAME`, `EMAIL`, `CREATED_DATE`, `UPDATED_DATE`' --table '`NoteShelf_DB`' '`NS_NOTES_META_DATA`' '`Note_shelf_DB`' '`NS_NOTES_META_DATA`' '`NOTE_ID`' '`NOTE_ID`' '`NOTE_ID`, `USER_ID`, `NOTE_NOSQL_ID`, `CREATED_DATE`, `UPDATED_DATE`' --table '`NoteShelf_DB`' '`NS_USER_AUTH_DETAIL`' '`Note_shelf_DB`' '`NS_USER_AUTH_DETAIL`' '`USER_AUTH_DETAIL_ID`' '`USER_AUTH_DETAIL_ID`' '`USER_AUTH_DETAIL_ID`, `USER_ID`, `PASSWORD`, `AUTH_TYPE`, `ACTIVE`, `LOCKED`, `CREATED_DATE`, `UPDATED_DATE`'

