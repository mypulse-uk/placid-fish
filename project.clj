(def version
  (or (System/getenv "VERSION")
      "2.0.0"))

(defproject ai.mypulse/placid-fish "2.0.0"
  :description "Safe url assertions for test classes that uses Exploding Fish under the hood"
  :url "https://github.com/Global-Online-Health/placid-fish"

  :license {:name "The MIT License"
            :url "https://opensource.org/licenses/MIT"}

  :dependencies [[org.clojure/clojure "1.11.1"]
                 [org.bovinegenius/exploding-fish "0.3.6"]]

  :plugins [[lein-cloverage "1.2.3"]
            [lein-shell "0.5.0"]
            [lein-ancient "0.7.0"]
            [lein-changelog "0.3.2"]
            [lein-eftest "0.5.9"]
            [lein-codox "0.10.8"]
            [lein-kibit "0.1.8"]
            [lein-bikeshed "0.5.2"]]

  :profiles
  {:shared {:dependencies
            [[org.clojure/clojure "1.11.1"]
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

  :bikeshed {:max-line-length 120}

  :deploy-repositories
  {"releases" {:url "https://repo.clojars.org" :creds :gpg}
   "snapshots" {:url "https://repo.clojars.org" :creds :gpg}})
