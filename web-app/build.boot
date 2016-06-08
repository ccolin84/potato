(set-env!
  ; Test path can be included here as source-files are not included in JAR
  ; Just be careful to not AOT them
  :source-paths #{"src/cljs" "src/less" "env/prod/cljs"}
  :resource-paths #{"resources"}
  :dependencies '[
                  [org.clojure/clojure    "1.8.0"]
                  [org.clojure/clojurescript "1.9.36"]
                  [boot/core              "2.6.0"      :scope "test"]
                  [adzerk/boot-cljs       "1.7.228-1"  :scope "test"]
                  [adzerk/boot-cljs-repl   "0.3.0"]
                  [deraen/boot-ctn "0.1.0"]
                  [com.cemerick/piggieback "0.2.1"  :scope "test"]
                  [weasel                  "0.7.0"  :scope "test"]
                  [org.clojure/tools.nrepl "0.2.12" :scope "test"]
                  
                  [cljs-http              "0.1.30"     ]
                  [org.omcljs/om             "0.9.0"]
                  [cljsjs/moment             "2.10.6-4"]

                  [deraen/boot-less       "0.5.0"      ]
                  ;; For boot-less
                  [org.slf4j/slf4j-nop    "1.7.21"     :scope "test"]])

(require
 '[deraen.boot-ctn :refer [init-ctn!]]
 '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[deraen.boot-less      :refer [less]])

(init-ctn!)

(task-options!
 cljs {:source-map true
       :compiler-options { :source-map-timestamp true}}
 less {:source-map true})

(deftask package
  "Build the package"
  []
  (comp
    (less :compression true)
    (cljs :optimizations :advanced)
    (target)))

(deftask dev 
  "Start the dev environment... "
  []
  (comp
   (watch)
   (less)
   (cljs-repl) ; order is important!!
   (cljs  :optimizations :none)))
