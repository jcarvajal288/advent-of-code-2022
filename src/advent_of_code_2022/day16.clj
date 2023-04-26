(ns advent-of-code-2022.day16
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.math.combinatorics :as cm]
            [taoensso.timbre :as log]
            [ubergraph.alg :as alg]
            [ubergraph.core :as uber]))

(defn parse-input [filename]
  (let [lines (get-resource-file-by-line filename)
        flow-rates (map #(re-find (re-matcher #"\d+" %)) lines)
        valves (map #(re-seq #"[A-Z]{2}" %) lines)]
    (zip valves flow-rates)))

(defn create-graph-node [graph next-line]
  (let [valves (first next-line)
        flow-rate (str-to-int (last next-line))
        current-valve (first valves)
        neighbor-valves (rest valves)]
    (-> graph
        (uber/add-nodes-with-attrs [current-valve {:flow-rate flow-rate}])
        (uber/add-undirected-edges* (map #(identity [current-valve %]) neighbor-valves)))))

(defn construct-valve-graph [filename]
  (->> (parse-input filename)
       (reduce (fn [graph next-line]
                 (create-graph-node graph next-line))
               (uber/graph))))

(defn calculate-all-shortest-paths [valve-graph open-valves]
  (->> open-valves
       (cm/subsets)
       (filter #(= (count %) 2))
       (map sort)
       (map #(identity [% (:cost (alg/shortest-path valve-graph {:start-node (first %) :end-node (last %)}))]))
       (into {})))

(defn find-optimal-pressure [valve-graph closed-valves shortest-paths current-valve minutes-left total-pressure-released pressure-rate]
  (let [other-valves (remove #{current-valve} closed-valves)]
    (if (or (empty? other-valves) (<= minutes-left 0))
      (+ total-pressure-released (* minutes-left pressure-rate))
      (let [pressure-list (map (fn [valve]
                                 (let [minutes-to-valve (inc (get shortest-paths (sort [valve current-valve])))
                                       minutes-spent (min minutes-left minutes-to-valve)
                                       valve-pressure-rate (:flow-rate (get (:attrs valve-graph) valve))]
                                   [valve (find-optimal-pressure
                                            valve-graph
                                            (remove #{valve} other-valves)
                                            shortest-paths
                                            valve
                                            (- minutes-left minutes-spent)
                                            (+ total-pressure-released (* minutes-spent pressure-rate))
                                            (+ pressure-rate valve-pressure-rate))]))
                               other-valves)
            best-valve (last (sort-by last pressure-list))]
        (last best-valve)))))

(defn most-possible-pressure [filename starting-valve total-minutes]
  (let [valve-graph (construct-valve-graph filename)
        all-valves (keys (:node-map valve-graph))
        working-valves (filter #(> (:flow-rate (get (:attrs valve-graph) %)) 0) all-valves)
        shortest-paths (calculate-all-shortest-paths valve-graph (conj working-valves starting-valve))]
    (log/info (count working-valves))
    (find-optimal-pressure valve-graph working-valves shortest-paths starting-valve total-minutes 0 0)))

(defn most-possible-pressure-with-elephant [filename starting-valve total-minutes]
  (let [valve-graph (construct-valve-graph filename)
        all-valves (keys (:node-map valve-graph))
        working-valves (filter #(> (:flow-rate (get (:attrs valve-graph) %)) 0) all-valves)
        shortest-paths (calculate-all-shortest-paths valve-graph (conj working-valves starting-valve))]
    (log/info (count working-valves))
    (->> (cm/combinations working-valves (quot (count working-valves) 2))
         (map #(identity [(find-optimal-pressure valve-graph % shortest-paths starting-valve total-minutes 0 0)
                         (find-optimal-pressure valve-graph (remove (set %) working-valves) shortest-paths starting-valve total-minutes 0 0)]))
         (map #(reduce + %))
         sort
         last)))
