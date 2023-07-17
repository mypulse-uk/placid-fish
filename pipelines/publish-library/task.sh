#!/usr/bin/env bash

[ -n "$DEBUG" ] && set -x
set -e
set -o pipefail

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
PROJECT_DIR="$( cd "$SCRIPT_DIR/../../.." && pwd )"

cd "$PROJECT_DIR"
cd source

export VERSION="2.0.1"

ls
pwd

echo $VERSION

mkdir -p ~/.lein

echo $CLOJARS_GPG_KEY | base64 --decode > /root/.lein/credentials.clj.gpg

chmod 0600 /root/.lein/credentials.clj.gpg

cat /root/.lein/credentials.clj.gpg

./go library:publish:release
