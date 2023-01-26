(ns advent-of-code-2022.day7
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]
            )
  (:import (java.io Writer)))

(deftype Directory [name parent children])
(defmethod print-method Directory [dir ^Writer writer]
  (.write writer (format "{%s %s [%s]}"
                         (.name dir)
                         (.parent dir)
                         (str/join #" " (map #(.name %) (.children dir))))))

(deftype File [name parent size])
(defmethod print-method File [file ^Writer writer]
  (.write writer (format "{%s %s %s}" (.name file) (.parent file) (.size file))))

(declare cd)
(declare dir)
(declare add-file)

(defn- cdt-reducer [tree current-dir commands]
  (let [cmd (first commands)
        tokens (if (= cmd nil) nil (str/split cmd #" "))]
    (log/info tree)
    (log/info (str "cmd: " cmd))
    (cond
      (empty? commands) tree
      (str/starts-with? cmd "$ cd") (cd tree current-dir tokens commands)
      (= (first tokens) "dir") (dir tree tokens current-dir commands)
      (is-number? (first tokens)) (add-file tree current-dir tokens commands)
      :else (cdt-reducer tree current-dir (rest commands)))))

(defn- cd [tree current-dir tokens commands]
  (let [dir-name (last tokens)
        current-node (get tree current-dir)]
    (log/info "in cd")
    (if (= dir-name "..")
      (cdt-reducer tree (:parent current-node) (rest commands))
      (cdt-reducer tree (last tokens) (rest commands)))))

(defn- dir [tree tokens current-dir commands]
  (let [current-node (get tree current-dir)
        new-dir (->Directory (last tokens) current-dir [])
        ]
    (log/info "in dir")
    (cdt-reducer
      (as-> (assoc tree (last tokens) new-dir) temp-tree
            (assoc temp-tree current-dir (->Directory (.name current-node)
                                                      (.parent current-node)
                                                      (conj (.children current-node) new-dir))))
      current-dir
      (rest commands))))

(defn- add-file [tree current-dir tokens commands]
  (let [current-node (get tree current-dir)
        file-size (first tokens)
        file-name (last tokens)
        new-file (->File file-name (:parent current-node) file-size)]
    (log/info "in add-file")
    (cdt-reducer
      (assoc tree current-dir (->Directory (.name current-node)
                                           (.parent current-node)
                                           (conj (.children current-node) new-file)))
      current-dir
      (rest commands))))

(defn construct-dir-tree [commands]
  (cdt-reducer {"/" (->Directory "/" nil [])} "/" commands))

(defn dir-size [name dir-tree]
  (let [reducer (fn [total directory dir-tree]
                  (if ))]
    (reducer 0 name dir-tree)))

(defn total-dir-size [size-limit commands]
  2)

