(ns gasoapi.core
  (:require [clojure.data.xml :as xml]
            [clojure.set :refer :all]
            [clojure.string :as str]
            [clj-time.core :as t]
            [formaterr.core :as frmt])
  (:gen-class))


(defn input []
  (frmt/csv "http://publicacionexterna.azurewebsites.net/publicaciones/preciopublicovigente"))

;; elviejo  "https://creboveda.blob.core.windows.net/transparencia/preciopublicovigente.csv"
(defn gas-ids
  [stations]
  (map #(assoc %1 :id %2) stations (rest (range))))

(defn write-xml
  [f body]
  (with-open [out-file (java.io.FileWriter. f)]
    (xml/emit body out-file)))

(defn elemeno
  [k v]
  (xml/element k {} v))

(defn location
  [station]
  (xml/element :location {}
               (elemeno :address_street (:calle station))
               (elemeno :x (:longitude station))
               (elemeno :y (:latitude station))))

(defn place
  [station]
  (xml/element :place
               {:place_id (:id station)}
               (elemeno :name (:razonsocial station))
               (elemeno :brand (:razonsocial station))
               (elemeno :category "GAS_STATION")
               (location station)))

(defn data-gas-stations
  [stations]
  (elemeno :places (map place stations)))

(defn price
  [station tipo]
  (if-not (empty? (get station tipo))
    (xml/element :gas_price
                 {:type (name tipo)
                  :update_time (str (t/now))}
                 (get station tipo))))

(defn station-prices
  [station]
  (xml/element :place
               {:place_id (:id station)}
               (price station :regular)
               (price station :premium)
               (price station :diesel)))

(defn data-gas-prices
  [stations]
  (elemeno :places
           (map station-prices stations)))

(defn -main
  []
  (let [data (gas-ids (input))]
    (write-xml "places.xml" (data-gas-stations data))
    (write-xml "prices.xml" (data-gas-prices data))
    (csv "catalogo-de-gasolineras.csv" input)))
