package id.ac.ui.cs.advprog.eshop.service;
import id.ac.ui.cs.advprog.eshop.model.Car;
import id.ac.ui.cs.advprog.eshop.service.interfaces.Create;
import id.ac.ui.cs.advprog.eshop.service.interfaces.Delete;
import id.ac.ui.cs.advprog.eshop.service.interfaces.RetrieveService;

public interface CarService extends Create<Car>, Delete, RetrieveService<Car> {
    // Bisa ditambahkan method spesifik jika dibutuhkan dimasa mendatang
    // Semisal CarService yang menggunakan mereturn method update yang void (berbeda dengan ProductService)
    // Sehingga user tidak memaksakan implementasi yang tidak sesuai
    void update(Car car);
}