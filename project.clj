(defproject ai.mypulse/placid-fish "0.1.0-SNAPSHOT"
  :description "Safe url assertions for test classes that uses Exploding Fish under the hood"
  :url "https://github.com/Global-Online-Health/placid-fish"

  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.10.0"]
                 [org.bovinegenius/exploding-fish "0.3.6"]]
  :main ^:skip-aot placid-fish.core

  :profiles
  {:shared {:dependencies
            [[org.clojure/clojure "1.11.1"]
             [ring/ring-mock "0.4.0"]
             [clj-time "0.15.2"]
             [faker "0.3.2"]
             [eftest "0.5.9"]]}
   :dev [:shared {:source-paths ["dev"]
                  :eftest {:multithread? false}}]
   :test [:shared {:eftest {:multithread? false}}]

   :prerelease
   {:release-tasks
    [["shell" "git" "diff" "--exit-code"]
     ["change" "version" "leiningen.release/bump-version" "rc"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit" "Pre-release version %s [skip ci]"]
     ["vcs" "tag"]
     ["deploy"]]}
   :release
   {:release-tasks
    [["shell" "git" "diff" "--exit-code"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["codox"]
     ["changelog" "release"]
     ["shell" "sed" "-E" "-i.bak" "s/\"[0-9]+\\.[0-9]+\\.[0-9]+\"/\"${:version}\"/g" "README.md"]
     ["shell" "rm" "-f" "README.md.bak"]
     ["shell" "git" "add" "."]
     ["vcs" "commit" "Release version %s [skip ci]"]
     ["vcs" "tag"]
     ["deploy"]
     ["change" "version" "leiningen.release/bump-version" "patch"]
     ["change" "version" "leiningen.release/bump-version" "rc"]
     ["change" "version" "leiningen.release/bump-version" "release"]
     ["vcs" "commit" "Pre-release version %s [skip ci]"]
     ["vcs" "tag"]
     ["vcs" "push"]]}}

  :target-path "target/%s/"

  :deploy-repositories
  {"releases" {:url "https://repo.clojars.org" :creds :gpg}
   "snapshots" {:url "https://repo.clojars.org" :creds :gpg}})
