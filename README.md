## Library Web Application :blue_book:

Bu proje Özgür Yazilim A.Ş 'ye staj başvurusu amacıyla geliştirilmiştir. 

* Java 8 ile kodlanmıştır. Çalıştırabilmek için öncelikle **java8 sdk** kurulmalı ve kullanılacak ide'de buna göre ayarların yapılması gerekmektedir.

* Kaynak dosyalar indirilip ide ile açıldıktan sonra *pom.xml* içerisindeki bağımlılıklarda bulunan jar'lar ide'nin desteklemesi halinde otomatik olarak indirelecektir.

* Veritabanı işlemleri için **postgresql** kullanılmıştır. Bu sebeple bilgisayarda kurulu olmalıdır. Veritabanı kurulumu için öncelikle pgAdmin uygulaması bilgisayarda açılıp **library** adında bir veritabanı oluşturulmalıdır. Uygulama konumunda *main/resources* :open_file_folder: altındaki *application.properties* dosyasında veritabanı bağlantısı için gerekli kullanıcı adı ve şifre girilmelidir. Varsayılan kullanıcı adı postgres tanımlanmıştır. Şifre ve bağlantı url'sindeki port kısmı bilgisayarınızdaki postgresql ayarlarınıza göre düzenlenmelidir.

Postgresql yerine **h2 database** de kullanılabilir. Bunun için *pom.xml* içerisinde bağımlılıklardan postgresql ile alakalı bağlılık kaldırılıp yerine h2 database bağlılığı eklenmelidir. h2 database bağlılık kodu için [tıklayın.](https://mvnrepository.com/artifact/com.h2database/h2/1.4.200)


