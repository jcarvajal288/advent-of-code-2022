(ns advent-of-code-2022.day16
  (:require [advent-of-code-2022.util :refer :all]
            [clojure.string :as str]
            [taoensso.timbre :as log]
            [ubergraph.core :as uber]
            [ubergraph.alg :as alg]))

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

;(defn expected-pressure-release [valve-graph open-valves current-valve end-valve minutes-left]
;  (let [distance-to-node (:cost (alg/shortest-path valve-graph {:start-node current-valve :end-node end-valve}))
;        flow-rate (:flow-rate (get (:attrs valve-graph) end-valve))]
;    (* flow-rate (- minutes-left distance-to-node))))

; recursively find the pressure released by each valve
(defn get-highest-pressure-release-valve [valve-graph open-valves current-valve minutes-left]
  (let [other-valves (remove #(= % current-valve) open-valves)
        costs-to-other-valves (map #(:cost (alg/shortest-path valve-graph {:start-node current-valve :end-node %})) other-valves)]
    (->> other-valves
         (map #(identity [% (get-highest-pressure-release-valve valve-graph other-valves current-valve (- minutes-left (get costs-to-other-valves %)))]))
         (sort-by last)
         reverse
         first
         first
         )))

(defn find-path-to-next-valve [valve-graph current-valve open-valves minutes-left]
  ;(log/info "Open valves: " open-valves)
  ;(log/info "Closed valves: " (vec (remove (set open-valves) (keys (:node-map valve-graph)))))
  (let [closed-valves (remove (set open-valves) (keys (:node-map valve-graph)))
        next-valve (get-highest-pressure-release-valve valve-graph closed-valves current-valve minutes-left)]
    (concat [current-valve] (map #(:dest %)
                                 (alg/edges-in-path
                                   (alg/shortest-path valve-graph {:start-node current-valve :end-node next-valve}))))))

(defn run-simulation [valve-graph path-to-next-valve open-valves total-pressure-released minutes-left]
  (let [current-valve (first path-to-next-valve)
        new-pressure-released (apply + (map #(:flow-rate (get (:attrs valve-graph) %)) open-valves))]
    (log/info (format "== %d minutes left ==" minutes-left))
    (log/info (format "Valves %s are open, releasing %d pressure" (str/join ", " open-valves) new-pressure-released))
    (log/info "Path: " (vec path-to-next-valve))
    (if (> (count path-to-next-valve) 1)
      (log/info (format "Moving to valve %s" (nth path-to-next-valve 1)))
      (log/info (format "Opening valve %s" current-valve)))
    (log/info "")
    (cond
      (= minutes-left 0) total-pressure-released
      (> (count path-to-next-valve) 1) (recur valve-graph
                                              (rest path-to-next-valve)
                                              open-valves
                                              (+ total-pressure-released new-pressure-released)
                                              (dec minutes-left))
      :else (let [new-open-valves (conj open-valves current-valve)
                  new-minutes-left (dec minutes-left)
                  new-path (find-path-to-next-valve valve-graph current-valve new-open-valves new-minutes-left)]
              (recur valve-graph
                     new-path
                     (conj open-valves current-valve)
                     (+ total-pressure-released new-pressure-released)
                     new-minutes-left)))))

(defn most-possible-pressure [filename starting-valve total-minutes]
  (let [valve-graph (construct-valve-graph filename)
        starting-path (find-path-to-next-valve valve-graph "AA" [] total-minutes)]
    (run-simulation valve-graph starting-path [] 0 total-minutes)))
