import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");

        Product product1 = new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics);
        Product product2 = new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones);
        Product product3 = new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories);

        List<Product> catalog = List.of(product1, product2, product3);
        Cart cart = new Cart();
        List<Order> orderHistory = new ArrayList<>(); //

        while (true) {
            System.out.println("\nВиберіть опцію:");
            System.out.println("1 - Переглянути список товарів");
            System.out.println("2 - Додати товар до кошика");
            System.out.println("3 - Переглянути кошик");
            System.out.println("4 - Видалити товар з кошика");
            System.out.println("5 - Зробити замовлення");
            System.out.println("6 - Переглянути історію замовлень");
            System.out.println("7 - Пошук товарів");
            System.out.println("0 - Вийти");

            if (!scanner.hasNextInt()) {
                System.out.println("Будь ласка, введіть число.");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("\nСписок товарів:");
                    for (Product p : catalog) {
                        System.out.println(p);
                    }
                break;
                case 2:
                    System.out.println("Введіть ID товару для додавання до кошика:");
                    int id = scanner.nextInt();
                    Product selectedProduct = catalog.stream()
                            .filter(p -> p.getId() == id)
                            .findFirst()
                            .orElse(null);

                    if (selectedProduct != null) {
                        cart.addProduct(selectedProduct);
                        System.out.println("Товар '" + selectedProduct.getName() + "' додано до кошика.");
                    } else {
                        System.out.println("Товар з таким ID не знайдено.");
                    }
                break;
                case 3:
                    System.out.println("\n" + cart);
                break;
                case 4:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній, немає чого видаляти.");
                    } else {
                        System.out.println("Введіть ID товару для видалення з кошика:");
                        int removeId = scanner.nextInt();
                        boolean removed = cart.removeProductById(removeId);
                        if (removed) {
                            System.out.println("Товар успішно видалено з кошика.");
                        } else {
                            System.out.println("Товар з таким ID не знайдено в кошику.");
                        }
                    }
                break;
                case 5:
                    if (cart.getProducts().isEmpty()) {
                        System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
                    } else {
                        Order order = new Order(cart);
                        orderHistory.add(order);
                        System.out.println("Замовлення оформлено:");
                        System.out.println(order);
                        cart.clear();
                    }
                break;
                case 6:
                    if (orderHistory.isEmpty()) {
                        System.out.println("Історія замовлень порожня.");
                    } else {
                        System.out.println("\n--- Історія замовлень ---");
                        for (int i = 0; i < orderHistory.size(); i++) {
                            System.out.println("Замовлення #" + (i + 1) + ":");
                            System.out.println(orderHistory.get(i));
                            System.out.println("-----------------------");
                        }
                    }
                    break;
                case 7:
                    System.out.println("Введіть назву товару або категорії для пошуку:");
                    String query = scanner.nextLine().trim().toLowerCase();

                    if (query.isEmpty()) {
                        System.out.println("Пошуковий запит не може бути порожнім.");
                        break;
                    }

                    List<Product> searchResults = catalog.stream()
                            .filter(p -> p.getName().toLowerCase().contains(query) ||
                                    (p.getCategory() != null && p.getCategory().getName().toLowerCase().contains(query)))
                            .toList();

                    if (searchResults.isEmpty()) {
                        System.out.println("Товарів за запитом '" + query + "' не знайдено.");
                    } else {
                        System.out.println("\n--- Результати пошуку ---");
                        for (Product p : searchResults) {
                            System.out.println(p);
                        }
                    }
                break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
                break;
            }
        }
    }
}