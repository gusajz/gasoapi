(defproject gasoapi "0.1.0-SNAPSHOT"
  :description "genera xmls de gasolineras de la API de CRE"
  :url "https://github.com/GOBMX/gasoapi"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/data.xml "0.2.0-alpha2"]
                 [clj-time "0.13.0"]
                 [formaterr "0.1.0-SNAPSHOT"]]
  :main gasoapi.core
  :repl-options {:init-ns gasoapi.core})
