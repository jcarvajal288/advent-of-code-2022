(ns advent-of-code-2022.day12
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.math.combinatorics :as cm]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]
            [taoensso.timbre :as log]
            ))

(defn get-cell [input-lines coord]
  (get (get input-lines (last coord)) (first coord)))

(defn translate-elevation [char]
  (cond
    (= char \S) (int \a)
    (= char \E) (int \z)
    :else (int char)))

(defn accessible-neighbor? [input-lines elevation neighbor]
  (let [neighbor-elevation (translate-elevation (get-cell input-lines neighbor))]
      (<= (- neighbor-elevation elevation) 1)))

(defn in-bounds? [input-lines coord]
  (not (nil? (get-cell input-lines coord))))

(defn find-accessible-neighbors [lines neighbor-coords elevation]
  (->> neighbor-coords
       (filter #(in-bounds? lines %))
       (filter #(accessible-neighbor? lines elevation %))))

(defn name-node [coord]
  (str (first coord) "-" (last coord)))

(defn create-graph-node [input-lines graph coord]
  (let [x (first coord)
        y (last coord)
        char (get-cell input-lines coord)
        elevation (translate-elevation char)
        neighbor-coords [[(+ x 1) y] [x (+ y 1)] [(- x 1) y] [x (- y 1)]]
        neighbors (find-accessible-neighbors input-lines neighbor-coords elevation)
        node-name (name-node coord)]
    (->> neighbors
         (map name-node)
         (reduce (fn [current-graph neighbor]
                   (uber/add-directed-edges current-graph [node-name neighbor]))
                   graph))))

(defn build-terrain-graph [input-lines]
  (let [width (count (first input-lines))
        height (count input-lines)]
    (->> (cm/cartesian-product (range 0 width) (range 0 height))
         (reduce (fn [graph next-coord]
                   (create-graph-node input-lines graph next-coord))
                 (uber/graph)))))

(defn find-node-with-value [input-lines char]
  (let [width (count (first input-lines))
        height (count input-lines)]
    (->> (cm/cartesian-product (range 0 width) (range 0 height))
         (filter #(= (get-cell input-lines %) char))
         (first))))

(defn shortest-path-to-signal [filename]
  (let [input-lines (get-resource-file-by-line filename)
        start (find-node-with-value input-lines \S)
        end (find-node-with-value input-lines \E)
        terrain-graph (build-terrain-graph input-lines)
        shortest-path (alg/shortest-path terrain-graph {:start-node (name-node start)
                                                        :end-node (name-node end)})]
    (dec (count (alg/nodes-in-path shortest-path)))))

(defn find-all-locations-with-elevation [input-lines char]
  (let [width (count (first input-lines))
        height (count input-lines)]
    (->> (cm/cartesian-product (range 0 width) (range 0 height))
         (filter #(= (get-cell input-lines %) char)))))

(defn shortest-of-any-path [filename]
  (let [input-lines (get-resource-file-by-line filename)
        end (find-node-with-value input-lines \E)
        terrain-graph (build-terrain-graph input-lines)
        all-a-locations (find-all-locations-with-elevation input-lines \a)]
    (->> all-a-locations
         (map #(alg/shortest-path terrain-graph {:start-node (name-node %)
                                                 :end-node (name-node end)}))
         (map #(alg/nodes-in-path %))
         (filter #(not (nil? %)))
         (map count)
         (sort)
         (first)
         (dec))))