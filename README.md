# Yapi Market Stok ve Siparis Takip Sistemi

## Proje Hakkinda

Bu proje, bir yapi market isletmesinin urun stok yonetimi, tedarikci takibi, depo yonetimi ve musteri siparisleri islemlerini web uzerinden gerceklestirmesini saglayan bir bilgi sistemidir. Internet Programlama dersi final projesi olarak gelistirilmistir.

## Kullanilan Teknolojiler

| Katman | Teknoloji |
|---|---|
| **Backend** | Java 17, Spring Boot 3.2.5, Spring MVC, Spring Data JPA, Hibernate |
| **Veritabani** | PostgreSQL |
| **Frontend** | Thymeleaf, HTML5, Bootstrap 5, Bootstrap Icons |
| **Sunucu** | GlassFish Server (WAR Deployment) |
| **Build Araci** | Apache Maven |

## 3 Katmanli Mimari (3-Tier Architecture)

Proje, yazilim muhendisligi prensiplerine uygun olarak 3 katmanli mimari ile gelistirilmistir:

### 1. Sunum Katmani (Presentation Layer) - Controller
- `@Controller` annotasyonu ile isaretlenmis siniflar
- HTTP isteklerini karsilar ve Thymeleaf gorunumlerine yonlendirir
- Is mantigi icermez, yalnizca HTTP mapping ve view yonetimi yapar
- Dosyalar: `controller/` paketi altinda

### 2. Is Mantigi Katmani (Business Logic Layer) - Service
- `@Service` annotasyonu ile isaretlenmis siniflar
- Tum is mantigi ve veri isleme bu katmanda gerceklesir
- Controller ve Repository katmanlari arasinda kopru gorevi gorur
- Dosyalar: `service/` paketi altinda

### 3. Veri Erisim Katmani (Data Access Layer) - Repository
- `@Repository` annotasyonu ile isaretlenmis arayuzler
- Spring Data JPA `JpaRepository` arayuzunu genisletir
- Veritabani islemlerini (CRUD) otomatik olarak saglar
- Dosyalar: `repository/` paketi altinda

```
com.student.inventory
├── controller/          ← Sunum Katmani
│   ├── HomeController
│   ├── ProductController
│   ├── CategoryController
│   ├── SupplierController
│   ├── WarehouseController
│   ├── CustomerOrderController
│   ├── OrderItemController
│   └── GlobalExceptionHandler
├── service/             ← Is Mantigi Katmani
│   ├── ProductService
│   ├── CategoryService
│   ├── SupplierService
│   ├── WarehouseService
│   ├── CustomerOrderService
│   └── OrderItemService
├── repository/          ← Veri Erisim Katmani
│   ├── ProductRepository
│   ├── CategoryRepository
│   ├── SupplierRepository
│   ├── WarehouseRepository
│   ├── CustomerOrderRepository
│   └── OrderItemRepository
├── model/               ← Domain Modeller (Entity)
│   ├── Product
│   ├── Category
│   ├── Supplier
│   ├── Warehouse
│   ├── CustomerOrder
│   └── OrderItem
└── InventoryApplication ← Ana Uygulama Sinifi
```

## Veritabani Tasarimi (6 Tablo)

Hibernate ORM, `spring.jpa.hibernate.ddl-auto=update` ayari sayesinde tum tablolari ve yabanci anahtarlari otomatik olusturur.

### Entity-Relationship (ER) Diyagrami

```
┌─────────────┐       ┌──────────────┐       ┌─────────────┐
│  Category    │       │   Product    │       │  Supplier   │
│─────────────│       │──────────────│       │─────────────│
│ PK: id      │──1:N──│ PK: id       │──N:1──│ PK: id      │
│ name        │       │ barcode      │       │ companyName │
│             │       │ name         │       │ contactName │
│             │       │ price        │       │ phone       │
│             │       │ stockQuantity│       │             │
│             │       │ FK:category_id│      │             │
│             │       │ FK:supplier_id│      │             │
│             │       │ FK:warehouse_id│     │             │
└─────────────┘       └──────┬───────┘       └─────────────┘
                             │
                             │ N:1
                      ┌──────┴───────┐
                      │  Warehouse   │
                      │──────────────│
                      │ PK: id       │
                      │ name         │
                      │ capacity     │
                      └──────────────┘

┌────────────────┐       ┌──────────────┐
│ CustomerOrder  │       │  OrderItem   │
│────────────────│       │──────────────│
│ PK: id         │──1:N──│ PK: id       │
│ orderDate      │       │ quantity     │
│ totalAmount    │       │ unitPrice    │
│ status         │       │ FK: order_id │
│                │       │ FK:product_id│──N:1── Product
└────────────────┘       └──────────────┘
```

### Tablo Iliskileri

| Iliski | Tur | Aciklama |
|---|---|---|
| Category → Product | One-to-Many (1:N) | Bir kategori birden fazla urun icerebilir |
| Supplier → Product | One-to-Many (1:N) | Bir tedarikci birden fazla urun saglayabilir |
| Warehouse → Product | One-to-Many (1:N) | Bir depoda birden fazla urun depolanabilir |
| Product → Category | Many-to-One (N:1) | Her urun bir kategoriye aittir |
| Product → Supplier | Many-to-One (N:1) | Her urun bir tedarikciye aittir |
| Product → Warehouse | Many-to-One (N:1) | Her urun bir depoda bulunur |
| CustomerOrder → OrderItem | One-to-Many (1:N) | Bir siparis birden fazla kalem icerebilir |
| OrderItem → CustomerOrder | Many-to-One (N:1) | Her siparis kalemi bir siparise aittir |
| OrderItem → Product | Many-to-One (N:1) | Her siparis kalemi bir urune aittir |

### Primary Key ve Foreign Key Yapisi

| Tablo | Primary Key | Foreign Key(s) |
|---|---|---|
| categories | id (BIGINT, AUTO) | - |
| suppliers | id (BIGINT, AUTO) | - |
| warehouses | id (BIGINT, AUTO) | - |
| products | id (BIGINT, AUTO) | category_id → categories(id), supplier_id → suppliers(id), warehouse_id → warehouses(id) |
| customer_orders | id (BIGINT, AUTO) | - |
| order_items | id (BIGINT, AUTO) | order_id → customer_orders(id), product_id → products(id) |

## GlassFish WAR Deployment Yapilandirmasi

Bu projenin GlassFish sunucusuna deploy edilebilmesi icin asagidaki yapilandirmalar yapilmistir:

### 1. pom.xml - WAR Paketleme
```xml
<packaging>war</packaging>
```

### 2. pom.xml - Tomcat Provided Scope
GlassFish kendi servlet container'ini saglayacagi icin, gomulu Tomcat bagimliliginin `provided` olarak isaretlenmesi gerekmektedir:
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-tomcat</artifactId>
    <scope>provided</scope>
</dependency>
```

### 3. SpringBootServletInitializer
Ana uygulama sinifi `SpringBootServletInitializer` sinifindan turetilmistir. Bu, WAR dosyasinin harici bir servlet container (GlassFish) tarafindan baslatilmasini saglar:
```java
@SpringBootApplication
public class InventoryApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InventoryApplication.class);
    }
}
```

### 4. Build ve Deploy Adimlari
```bash
# WAR dosyasini olusturma
mvn clean package

# Olusturulan dosya: target/inventory-system.war
# Bu dosya GlassFish admin paneli uzerinden deploy edilir.
```

## Hata Yonetimi

- `@ControllerAdvice` annotasyonu ile global hata yakalama mekanizmasi kurulmustur
- `DataIntegrityViolationException`: Iliskili kayit silme hatalari icin kullanici dostu mesaj gosterilir
- `RuntimeException`: Kayit bulunamadi gibi hatalar icin ozel mesaj gosterilir
- `Exception`: Beklenmeyen hatalar icin genel hata sayfasi gosterilir
- **Stack trace hicbir zaman kullaniciya gosterilmez**

## Form Dogrulama (Validation)

- `@NotBlank`: Bos metin alanlari icin
- `@NotNull`: Zorunlu iliskisel alanlar (dropdown) icin
- `@Min`: Negatif deger girisini engellemek icin
- Hatalar form uzerinde Bootstrap `is-invalid` sinifi ile gorsel olarak gosterilir

## Kurulum

### Gereksinimler
- Java 17+
- Maven 3.8+
- PostgreSQL 14+
- GlassFish 7+

### Veritabani Olusturma
```sql
CREATE DATABASE inventorydb;
```

### application.properties Ayarlari
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/inventorydb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
```

### Calistirma
```bash
# Gelistirme ortaminda
mvn spring-boot:run

# WAR olusturma
mvn clean package
```

---

