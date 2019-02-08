# Statify

When data(fy) is not enough, we must Statify!!

## Usage

Meant for usage inside [REBL](https://github.com/cognitect-labs/REBL-distro), although not required.
```clojure

(require '[net.joelane.statify :as stat])

(stat/statify [1 2 3 
               #{1 2 3}
               (list 1 2 3)
               {:foo [4 5 6]
                :bar #{7 8 9}}])
;; => [1 2 3 #{1 3 2} (1 2 3) {:foo [4 5 6], :bar #{7 9 8}}]
```
Nothing happened!?
```clojure 
(meta *1)

;; => {:data [1 2 3 #{1 3 2} (1 2 3) {:foo [4 5 6], :bar #{7 9 8}}],
;;     :count 6,
;;     :sum 6,
;;     :min 1,
;;     :max 3,
;;     :mean 2,
;;     clojure.core.protocols/nav
;;     #object[net.joelane.statify$with_statify_nav$fn__9438 0x7ee1347a "net.joelane.statify$with_statify_nav$fn__9438@7ee1347a"]}
```

Ahh,that's right, because data about data isn't the data, its _metadata_... 


```
## License

Copyright © 2019 Joe Lane

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
