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

echo "$CLOJARS_DEPLOY_USERNAME"

mkdir -p ~/.lein

./go library:publish:release
