# Android exercises
## Exercises 3
### Task in polish
1. Zaimplementuj usługę, która daje możliwość komunikacji ze sobą (Bound Service)
- usługa zostaje uruchomiona przy tworzeniu aktywności
- usługa zostaje uruchomiona w osobnym wątku w tym samym procesie co aplikacja
- po uruchomieniu usługi wyświetlany jest monit „Your bound service has been started”
- co jakiś czas wyświetlany jest monit „Your bound service is still working”
- klasa usługi zawiera zmienną, która co jakiś czas jest inkrementowana a po kliknięciu w przycisk „Get counter from bind service” zostaje wyświetlony monit z aktualną wartością tej zmiennej
- po kliknięciu w przycisk „Go to list activity” i przejściu do nowej aktywności usługa zostaje zatrzymana
