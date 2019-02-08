(ns net.joelane.statify
  (:require
    [clojure.datafy :as dfy])
  (:import (clojure.lang IPersistentMap IPersistentList IPersistentSet IPersistentVector)))

(defprotocol Statifiable
  (-statify [_] "Impl detail, do not call"))

(defn statify
  [x]
  (-statify x))

(defn- with-statify-nav
  [xs]
  (let [nums (filter number? xs)
        counted? (counted? xs)
        element-count (when counted? (count xs))
        num-count (when counted? (count nums))
        min (apply min nums)
        max (apply max nums)
        sum (apply +' nums)
        mean (when counted? (/ sum num-count))]
    (vary-meta
      xs
      merge
      (cond-> {:data (dfy/datafy xs)}
              counted? (assoc :count element-count)
              sum (assoc :sum sum)
              min (assoc :min min)
              max (assoc :max max)
              mean (assoc :mean mean))
      {'clojure.core.protocols/nav
       (fn [_ _ v]
         (statify v))})))

(extend-protocol Statifiable
  Object
  (-statify [x] x)

  nil
  (-statify [_] nil)

  IPersistentMap
  (-statify [xs]
    (vary-meta
      xs
      merge
      {'clojure.core.protocols/nav
       (fn [_ _ v]
         (statify v))}))

  IPersistentList
  (-statify [xs] (with-statify-nav xs))

  IPersistentSet
  (-statify [xs] (with-statify-nav xs))

  IPersistentVector
  (-statify [xs] (with-statify-nav xs)))
