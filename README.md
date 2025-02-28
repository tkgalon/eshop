# eshop
---
Nama  : Muhammad Zaid Ats Tsabit <br>
NPM   : 2306224410 <br>
Kelas : Pemograman Lanjut B
---
### Refleksi 1

Pada awalnya, saya masih kurang menangkap alur dari controller, repository, dan juga service.
Namun, setelah mengerjakan bagian fitur edit dan delete, pemahaman saya terhadap alur tersebut semakin jelas.
Dimana controller sebagai "gerbang awal" dalam menangani request, lalu diteruskan kedalam service untuk masalah logikanya,
dan terakhir masuk ke repository untuk mengurus data yang dibutuhkan.

Tentunya pada latihan ini, saya mencoba menerapkan konsep _**clean code**_ dan juga _**secure coding**_.
Konsep _clean code_ yang saya gunakan seperti _**meaningful names**_, dimana saya sebisa mungkin membuat suatu nama method atau variable
yang _**self describe**_.

```java
    @GetMapping("/edit/{id}")
    public String editProductPage(@PathVariable String id, Model model) {
        Product product = service.findById(id);
        if (product == null) {
            return "redirect:list";
        }
        model.addAttribute("product", product);
        return "EditProduct";
    }
```
Lalu, ada _**small fuction**_ yang secara spesifik menyelesaikan tugas secara khusus.
```java
@Override
    public Product findById(String id) {
        return productRepository.findById(id);
    }
```
Di latihan 1 ini belum ada _comments_ yang menjelaskan suatu method atau cara kerja code.
Hal ini disebabkan method dan penamaan variable masih _straightforward_ atau _self describe_.

Dan terakhir konsep _**secure coding**_, saya memvalidasi input-input yang ada, seperti di dalam fitur _edit product_.
```java
    @PostMapping("/edit")
    public String editProductPost(@ModelAttribute Product product, Model model) {
        if (product.getProductName() == null || product.getProductName().isEmpty()) {
            model.addAttribute("error", "Product name is required");
            return "EditProduct";
        }
        if (product.getProductQuantity() < 0) {
            model.addAttribute("error", "Quantity cannot be negative");
            return "EditProduct";
        }

        service.edit(product);
        return "redirect:list";
    }
```

Untuk kekurangan dan kesalahannya, masih banyak yang harus ditingkatkan dari segi keamanan codenya itu sendiri, seperti _handling error_ ( _best practice_ )
dan masih perlu untuk memahami lebih lanjut alur dari konsep pada _spring_ ini.

### Refleksi 2

#### After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors? 

Setelah menulis unit test, saya merasa ternyata cukup rumit pada awalnya karena harus memikirkan kasus-kasus yang mungkin terjadi di fitur kita.
Saya sendiri tidak kebayang bagaimana pusingnya dalam membuat unit test pada proyek yang lebih besar, sepertinya bakal sangat pusing.
Namun, tentunya unit test akan sangat membantu jika mengembangkan suatu proyek dari skala kecil hingga besar.
Menurut saya dalam sebuah kelas, jumlah unit test yang dibuat harus cukup untuk mencakup semua fungsionalitas penting, baik skenario positif maupun negatif, seperti yang dilakukan pada test Edit dan Delete.
Selain itu untuk memastikan bahwa unit test sudah cukup, kita juga perlu memeriksa _**code coverage**_, yang menunjukkan seberapa besar bagian kode yang telah diuji.
Meskipun kita sudah menyentuh 100% code coverage, itu tidak menjamin kode bebas dari bug atau error karena _**code coverage**_ hanya memastikan test-test yang sudah kita _define_
sehingga masih ada kemungkinan kasus-kasus yang belum dimasukan kedalam test. Yang mana tentunya jika hal tersebut terjadi, masih ada ruang untuk bugs atau kesalahan.


#### Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Tentunya functional test tersebut akan membuat redundasi kode kita, dimana memiliki struktur, nama, dan instansiasi yang sama.
Hal ini mungkin akan menyalahi konsep _clean code_ yang mengedepankan prinsip menghindari redundansi fungsi yang sama.
Redundansi ini bisa menyebabkan kesulitan dalam pemeliharaan dan pengembangan lebih lanjut karena setiap perubahan yang diperlukan di satu bagian kode harus diperbarui di berbagai tempat.
Mungkin saran saya, Jika bisa kita buat file untuk setup struktur testnya sehingga bisa diinherit ( mungkin ) oleh functional test lainnya, yang mana akan mengurangi redundansi kode.



## Reflection Modul 2

###  Code Quality Issues & Fixes
Selama proses CI/CD ini, ada beberapa **masalah kualitas kode** yang berhasil diperbaiki:
1. **Unused Import**
    - Masalah ini terjadi karena terdapat import yang tidak digunakan di `ProductController.java`.  Oleh karena itu, saya menghapus import yang tidak digunakan dan menggantinya dengan mengimport spesifik library untuk meningkatkan efisiensi kode.

2. **Unnecessary Modifier in Interface**
    - Masalah ini disebabkan oleh modifier `public` yang tidak diperlukan dalam method di `ProductService.java`. Yang mana hal tersebut dibenarkan dengan cara  menghapus modifier `public` karena method dalam `interface` sudah otomatis bersifat `public abstract`.


Perbaikan ini dilakukan dengan **mengikuti rekomendasi dari PMD & CI/CD pipeline**, memastikan kode lebih bersih, lebih efisien, dan sesuai best practice.

---

### CI/CD Implementation & Reflection
Menurut saya proses CI/CD yang diimplementasikan dalam repository ini sudah memenuhi **definisi Continuous Integration (CI) dan Continuous Deployment (CD)**. Hal ini bisa dilihat dari CI yang sudah bisa berjalan otomatis pada setiap push dan pull request.
Dimana setiap ada perubahan yang di-push ke repository, maka Github Actions akan langsung menjalankan **unit test dan juga code quality analysis (PMD)**.
Dengan ada nya **test unit otomatis dan PMD untuk analisis kode**, kesalahan perubahan kode yang dibuat bisa dapat terdeteksi terlebih dahulu sebelum di deploy. Yang mana hal tersebut saya lakukan untuk fix masalah kualitas kode.
Lalu, setelah CI berhasil, maka merging branch bisa dilakukan ke main untuk menjalankan deploy otomatis ke dalam Koyeb, yang mana ini sesuai dengan **Continuous Deployment (CD)**.


## Reflection Modul 3

### Implementasi SOLID

1. **Single Responsibility Principle (SRP)** <br>
   - Disini saya memisahkan product controller dan juga car controller sebagai class yang terpisah sehingga keduanya memiliki responsibily yang khusus dan berbeda.
2. **Open-Closed Principle (OCP)**
   - Membuat repository generic sehingga jika perlu membuat repository baru tinggal menambah fungsionalitas baru.
     Dalam tugas ini, Product dan Car repository mengimplementasi Generic repository karena keduanya memiliki fungsionalitas yang mirip-mirip.
     Selain itu, hal ini berguna untuk memastikan entitas repository terbuka secara perluasan (open), tetapi terutup untuk modifikasi (closed) 
    sehingga jika kita membutuhkan fungsi yang baru di repository, kita tinggal menambahkannya secara langsung di repository spesifik tersebut.
3. **Liskov Substitution Principle (LSP)** <br>
    - Saya menghapus relasi inheritance pada Product dan Car Controller karena kelas turunannya, yaitu CarController, tidak dapat digunakan sebagai pengganti objek dari kelas induk ( sebab keduanya tidak ada hubungan parent-child ).
4. **Interface Segregation Principle (ISP)**<br>
    - Pada service, saya memecah method-method seperti CRUD dan RetrievalService menjadi interface tersendiri. Hal tersebut dilakukan agar fungsi tersebut menjadi modular
      sehingga ketika suatu service membutuhkan fungsi tersebut, tinggal melakukan extends karena keduanya merupakan interface. 
      Dengan melakukan tersebut, kita tidak memaksakan service untuk mengimplementasikan antarmuka (interface) yang tidak relevan bagi mereka.
5. **Dependency Inversion Principle (DIP)** <br>
   - Disini CarController seharusnya tidak bergantung pada concrete class karena sebagai modul level tinggi seharusnya bergantung pada level abstraksi.
     Oleh karena itu, saya menghubungkannya pada interface ProductService, bukan ProductServiceImpl.

### KEUNTUNGAN MENGGUNAKAN SOLID
Tentunya struktur projek kita menjadi lebih jelas dan mudah untuk dibaca oleh orang lain jika berkolaborasi.
Hal tersebut karena kita sudah membuat suatu Class atau Fungsi bekerja untuk satu tanggung jawab saja, tidak bercampur.
Selain itu, memudahkan juga untuk kita dalam menambah fungsionalitas baru tanpa harus mengubah kode yang ada, contohnya menambahkan repository yang spesifik.
Lalu, dengan memodularkan suatu fungsi kita bisa secara fleksibel membuat suatu service yang membutuhkan implementasi yang khusus atau spesifik. Dan terakhir dengan memastikan modul level tinggi
bergantung pada abstrak level, akan memudahkan kita dalam memperluas fungsi tanpa memodifikasi bagian lain.
Pada akhirnya solid ini akan membantu kita dalam pemeliharaan kode, fleksibilitas, dan scalability.

### KERUGIAN TIDAK MENGGUNAKAN SOLID
Tanpa menerapkan prinsip SOLID, struktur proyek kita bisa menjadi kurang jelas dan sulit dipahami, terutama jika kita bekerja dalam tim. Misalnya, ketika kita tidak membagi tanggung jawab dengan jelas antar kelas atau fungsi, maka kode bisa jadi tercampur aduk dan sulit untuk dimodifikasi tanpa mempengaruhi bagian lain. Hal ini membuat kolaborasi antar pengembang menjadi lebih rumit karena setiap perubahan bisa berdampak besar pada kode yang sudah ada. Selain itu, menambah fungsionalitas baru jadi lebih berisiko karena kita harus mengubah kode yang sudah ada, yang bisa menyebabkan bug baru muncul. Misalnya, jika kita ingin menambah repository spesifik, kita mungkin harus mengubah banyak bagian lain dari aplikasi, yang tidak ideal. Tanpa memodularkan fungsi, kita juga kesulitan membuat service yang spesifik dan fleksibel sesuai kebutuhan. Terakhir, tanpa Dependency Inversion Principle (DIP), kita bisa jadi bergantung langsung pada detail implementasi, yang membuat aplikasi kurang fleksibel dan sulit untuk diperluas. Pada akhirnya, tanpa SOLID, kita akan mengalami kesulitan dalam pemeliharaan kode, fleksibilitas, dan scalability, dan rentan terhadap kerusakan jangka panjang pada aplikasi.

