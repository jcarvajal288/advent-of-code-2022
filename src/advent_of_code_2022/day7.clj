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

(defn add-file-to-tree [tree file]
  (let [new-file-tree (assoc (get tree :files) (.name file) file)]
    (assoc tree :files new-file-tree)))

(defn get-file-from-tree [tree file-name]
  (get (get tree :files) file-name))

(defn get-node [tree node-name]
  (let [current-node (get tree node-name)]
    (if (nil? current-node)
      (get-file-from-tree tree node-name)
      current-node)))

(defn- cdt-reducer [tree current-dir commands]
  (let [cmd (first commands)
        tokens (if (= cmd nil) nil (str/split cmd #" "))]
    (log/info "-----")
    (log/info tree)
    (log/info cmd)
    (log/info (str "current-dir: " current-dir))
    (cond
      (empty? commands) tree
      (str/starts-with? cmd "$ cd") (cd tree current-dir tokens commands)
      (= (first tokens) "dir") (dir tree tokens current-dir commands)
      (is-number? (first tokens)) (add-file tree current-dir tokens commands)
      :else (cdt-reducer tree current-dir (rest commands)))))

(defn- cd [tree current-dir tokens commands]
  (let [dir-name (last tokens)
        current-node (get tree current-dir)]
    (if (= dir-name "..")
      (cdt-reducer tree (:parent current-node) (rest commands))
      (cdt-reducer tree (last tokens) (rest commands)))))

(defn- dir [tree tokens current-dir commands]
  (let [current-node (get tree current-dir)
        new-dir-name (last tokens)
        new-dir (->Directory new-dir-name current-dir [])]
    (log/info (str "current-node: " (.name current-node)))
    (cdt-reducer
      (if (contains? tree  new-dir-name)
        tree
        (as-> (assoc tree new-dir-name new-dir) temp-tree
              (assoc temp-tree current-dir (->Directory (.name current-node)
                                                        (.parent current-node)
                                                        (conj (.children current-node) new-dir)))))
      current-dir
      (rest commands))))

(defn- add-file [tree current-dir tokens commands]
  (let [current-node (get tree current-dir)
        file-size (str-to-int (first tokens))
        file-name (last tokens)
        new-file (->File file-name (:parent current-node) file-size)
        tree-with-new-file (add-file-to-tree tree new-file)]
    (log/info tree)
    (log/info tree-with-new-file)
    ;(log/info current-node)
    (cdt-reducer
      (assoc tree-with-new-file current-dir (->Directory (.name current-node)
                                           (.parent current-node)
                                           (conj (.children current-node) new-file)))
      current-dir
      (rest commands))))

(defn construct-dir-tree [commands]
  (cdt-reducer {"/" (->Directory "/" nil [])} "/" commands))

(defn dir-size-reducer [total-size node-name dir-tree]
  (let [current-node (get-node dir-tree node-name)]
    (if (instance? File current-node)
      (+ total-size (.size current-node))
      (map #(dir-size-reducer total-size (.name %) dir-tree) (.children current-node))
      )))

(defn dir-size [name dir-tree]
  (reduce + (flatten (dir-size-reducer 0 name dir-tree))))

(defn total-dir-size [size-limit commands]
  (let [dir-tree (construct-dir-tree commands)]
  (->> dir-tree
       (vals)
       (filter #(instance? Directory %))
       (map #(.name %))
       (map #(dir-size % dir-tree))
       (filter #(<= % size-limit))
       (reduce +)
       )))

