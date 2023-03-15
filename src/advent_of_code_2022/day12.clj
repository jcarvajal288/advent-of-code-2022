(ns advent-of-code-2022.day12
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.math.combinatorics :as cm]
            [ubergraph.core :as uber]
            [taoensso.timbre :as log]
            ))

(def ^:dynamic start [0 0])
(def ^:dynamic end [0 0])

(defn get-cell [lines coord]
  (get (get lines (last coord)) (first coord)))

(defn translate-elevation [char]
  (cond
    (= char \S) (int \a)
    (= char \E) :end
    :else (int char)))

(defn accessible-neighbor? [lines elevation neighbor]
  (let [neighbor-elevation (translate-elevation (get-cell lines neighbor))]
    (cond
      (= elevation :end) false
      (= neighbor-elevation :end) true
      :else (<= (- neighbor-elevation elevation) 1))))

(defn in-bounds? [lines coord]
  (not (nil? (get-cell lines coord))))

(defn find-accessible-neighbors [lines neighbor-coords elevation]
  (->> neighbor-coords
       (filter #(in-bounds? lines %))
       (filter #(accessible-neighbor? lines elevation %))
       ))

(defn name-node [coord]
  (str (first coord) "-" (last coord)))

(defn create-graph-node [lines graph coord]
  (let [x (first coord)
        y (last coord)
        char (get-cell lines coord)
        elevation (translate-elevation char)
        neighbor-coords [[(+ x 1) y] [x (+ y 1)] [(- x 1) y] [x (- y 1)]]
        neighbors (find-accessible-neighbors lines neighbor-coords elevation)
        node-name (name-node coord)
        ]
    (do (if (= char \S) (binding [start coord]))
        (if (= char \E) (binding [end coord]))
        (->> neighbors
             ;(map (fn [x] [(name-node coord) (name-node x)]))
             (map name-node)
             (reduce (partial uber/build-graph graph [node-name %]) graph))
    )))
; https://github.com/Engelberg/ubergraph

(defn read-map-from-file [filename]
  (let [lines (get-resource-file-by-line filename)
        width (count (first lines))
        height (count lines)
        graph (uber/graph [])]
    (->> (cm/cartesian-product (range 0 width) (range 0 height))
         (reduce (partial create-graph-node lines) graph)
         (uber/pprint)
         )))
