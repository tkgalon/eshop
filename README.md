# eshop

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


