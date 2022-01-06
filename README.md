Zespół 4
========

Skład: Mateusz Brzozowski, Konrad Wojda, Mikołaj Kuranowski


Wymagane konstrukcje
--------------------

- [x] [wykorzystanie pętli for-each](src/main/java/edu/iipw/pap/db/model/Pattern.java#L266)
- [x] wykorzystanie jednego z następujących elementów:
    - [x] [typ wyliczeniowy](src/main/java/edu/iipw/pap/db/model/WheelchairAccessibility.java)
    - [ ] własny typ generyczny
    - [ ] zmienna liczba parametrów metody
    - [ ] własna adnotacja
    - [ ] wyrażenie lambda
- [ ] wykorzystanie strumieniowego przetwarzania danych w kolekcjach
- [ ] zdefiniowanie testów jednostkowych dla metod publicznych w modelu (zakładając, że stosujemy MVC):
    - [ ] sytuacje poprawne
    - [ ] sytuacje niepoprawne
    - [ ] sytuacje graniczne
- [ ] definiowanie i rzucenie własnego wyjątku w modelu i jego obsługa w kontrolerze
    - [x] [definiowanie wyjątku](src/main/java/edu/iipw/pap/exceptions/InvalidData.java)
    - [x] [rzucenie go w modelu](src/main/java/edu/iipw/pap/db/model/Agency.java#L227)
    - [ ] obsługa w kontrolerze
