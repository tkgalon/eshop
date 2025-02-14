# eshop
---
Nama  : Muhammad Zaid Ats Tsabit <br>
NPM   : 2306224410 <br>
Kelas : Pemograman Lanjut B
---
## Refleksi 1

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

## Refleksi 1

### After writing the unit test, how do you feel? How many unit tests should be made in a class? How to make sure that our unit tests are enough to verify our program? It would be good if you learned about code coverage. Code coverage is a metric that can help you understand how much of your source is tested. If you have 100% code coverage, does that mean your code has no bugs or errors? 

Setelah menulis unit test, saya merasa ternyata cukup rumit pada awalnya karena harus memikirkan kasus-kasus yang mungkin terjadi di fitur kita.
Saya sendiri tidak kebayang bagaimana pusingnya dalam membuat unit test pada proyek yang lebih besar, sepertinya bakal sangat pusing.
Namun, tentunya unit test akan sangat membantu jika mengembangkan suatu proyek dari skala kecil hingga besar.
Menurut saya dalam sebuah kelas, jumlah unit test yang dibuat harus cukup untuk mencakup semua fungsionalitas penting, baik skenario positif maupun negatif, seperti yang dilakukan pada test Edit dan Delete.
Selain itu untuk memastikan bahwa unit test sudah cukup, kita juga perlu memeriksa _**code coverage**_, yang menunjukkan seberapa besar bagian kode yang telah diuji.
Meskipun kita sudah menyentuh 100% code coverage, itu tidak menjamin kode bebas dari bug atau error karena _**code coverage**_ hanya memastikan test-test yang sudah kita _define_
sehingga masih ada kemungkinan kasus-kasus yang belum dimasukan kedalam test. Yang mana tentunya jika hal tersebut terjadi, masih ada ruang untuk bugs atau kesalahan.


### Suppose that after writing the CreateProductFunctionalTest.java along with the corresponding test case, you were asked to create another functional test suite that verifies the number of items in the product list. You decided to create a new Java class similar to the prior functional test suites with the same setup procedures and instance variables.What do you think about the cleanliness of the code of the new functional test suite? Will the new code reduce the code quality? Identify the potential clean code issues, explain the reasons, and suggest possible improvements to make the code cleaner!

Tentunya functional test tersebut akan membuat redundasi kode kita, dimana memiliki struktur, nama, dan instansiasi yang sama.
Hal ini mungkin akan menyalahi konsep _clean code_ yang mengedepankan prinsip menghindari redundansi fungsi yang sama.
Redundansi ini bisa menyebabkan kesulitan dalam pemeliharaan dan pengembangan lebih lanjut karena setiap perubahan yang diperlukan di satu bagian kode harus diperbarui di berbagai tempat.
Mungkin saran saya, Jika bisa kita buat file untuk setup struktur testnya sehingga bisa diinherit ( mungkin ) oleh functional test lainnya, yang mana akan mengurangi redundansi kode.
