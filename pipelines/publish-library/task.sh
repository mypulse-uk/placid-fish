#!/usr/bin/env bash

[ -n "$DEBUG" ] && set -x
set -e
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$( cd "$SCRIPT_DIR/../../.." && pwd )"

cd "$PROJECT_DIR"
cd source

ls
pwd

mkdir -p ~/.lein

./go library:publish:prepare

chmod 0600 ~/.lein/credentials.clj.gpg

./go library:publish:release
