import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static java.util.Collections.binarySearch;

class GameBinary {
    public static class Game {
        private String name;
        private String category;
        private int price;
        private int quality;

        public Game(String name, String category, int price, int quality) {
            this.name = name;
            this.category = category;
            this.price = price;
            this.quality = quality;
        }

        String getName() {
            return name;
        }

        String getCategory() {
            return category;
        }

        int getPrice() {
            return price;
        }

        int getQuality() {
            return quality;
        }
    }

    public static class Dataset {
        private ArrayList<Game> data;
        private String sortedByAttribute;

        public Dataset(ArrayList<Game> data) {
            this.data = data;
        }

        public ArrayList<Game> getGamesByPrice(int price) {
            ArrayList<Game> gamePrecios = new ArrayList<>();
            boolean estaOrdenado = true;
            for (int i = 0; i < data.size() - 1; i++) {
                Game inicio = data.get(i);
                Game fin = data.get(i + 1);
                if (inicio.getPrice() > fin.getPrice()) {
                    estaOrdenado = false;
                    break;
                }
            }
            if (estaOrdenado) {
                // Crea objeto con el precio a comparar
                Game precioAux = new Game("", "", price, 0);
                // Encuentra la posicion en donde se encuentra el objeto con el precio solicitado mediante binarySearch
                int precioBuscar = binarySearch(data, precioAux, Comparator.comparingInt(Game::getPrice));

                if (precioBuscar >= 0) {
                    int mid = precioBuscar;
                    int izquierda = mid - 1;
                    int derecha = mid + 1;

                    gamePrecios.add(data.get(mid));
                    // Recorre el arreglo por la izquierda, para verificar si mas objetos contienen el mismo precio
                    while (izquierda >= 0 && data.get(izquierda).getPrice() == price) {
                        gamePrecios.add(data.get(izquierda));
                        izquierda--;
                    }
                    // La misma funcion anterior pero hacia la derecha
                    while (derecha < data.size() && data.get(derecha).getPrice() == price) {
                        gamePrecios.add(data.get(derecha));
                        derecha++;
                    }
                    return gamePrecios;
                }
            } else {
                // Compara los objetos de manera lineal y los agrega a la lista
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getPrice() == price) {
                        gamePrecios.add(data.get(i));
                    }
                }
                return gamePrecios;
            }
            return null;
        }

        public ArrayList<Game> getGamesByPriceRange(int lowerPrice, int higherPrice) {
            ArrayList<Game> gameRango = new ArrayList<>();
            boolean estaOrdenado = true;
            for (int i = 0; i < data.size() - 1; i++) {
                Game inicio = data.get(i);
                Game fin = data.get(i + 1);
                if (inicio.getPrice() > fin.getPrice()) {
                    estaOrdenado = false;
                    break;
                }
            }
            if (estaOrdenado) {
                // Busca la posicion del objeto con el precio minimo solicitado
                Game precioLow = new Game("", "", lowerPrice, 0);
                int precioBuscarLow = binarySearch(data, precioLow, Comparator.comparingInt(Game::getPrice));
                // Si no se encuentra, indica el indice en donde podria insertarse
                if (precioBuscarLow < 0) {
                    precioBuscarLow = -precioBuscarLow - 1;
                }
                // Va agregando al arreglo los juegos que estan dentro del rango
                while (precioBuscarLow < data.size() && data.get(precioBuscarLow).getPrice() <= higherPrice) {
                    gameRango.add(data.get(precioBuscarLow));
                    precioBuscarLow++;
                }

            } else {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getPrice() >= lowerPrice && data.get(i).getPrice() <= higherPrice) {
                        gameRango.add(data.get(i));

                    }
                }
            }
            return gameRango;
        }

        public ArrayList<Game> getGamesByCategory(String category) {
            ArrayList<Game> gameCategoria = new ArrayList<>();
            boolean estaOrdenado = true;
            for (int i = 0; i < data.size() - 1; i++) {
                if (data.get(i).getCategory().compareTo(data.get(i + 1).getCategory()) > 0) {
                    estaOrdenado = false;
                    break;
                }
            }
            if (estaOrdenado) {
                Game Categoria = new Game("", category, 0, 0);
                int categoriaBuscar = binarySearch(data, Categoria, Comparator.comparing(Game::getCategory));

                if (categoriaBuscar < 0) {
                    categoriaBuscar = -categoriaBuscar - 1;
                }
                int mid = categoriaBuscar;
                int izquierda = mid - 1;
                int derecha = mid + 1;

                gameCategoria.add(data.get(mid));

                while (izquierda >= 0 && data.get(izquierda).getCategory().equals(category)) {
                    gameCategoria.add(data.get(izquierda));
                    izquierda--;
                }

                while (derecha < data.size() && data.get(derecha).getCategory().equals(category)) {
                    gameCategoria.add(data.get(derecha));
                    derecha++;
                }
                Collections.reverse(gameCategoria);
                return gameCategoria;
            } else {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getCategory().equals(category)) {
                        gameCategoria.add(data.get(i));
                    }
                }
            }
            return gameCategoria;
        }

        public ArrayList<Game> getGamesByQuality(int quality) {
            ArrayList<Game> gameCalidad = new ArrayList<>();
            boolean estaOrdenado = true;
            for (int i = 0; i < data.size() - 1; i++) {
                Game inicio = data.get(i);
                Game fin = data.get(i + 1);
                if (inicio.getQuality() > fin.getQuality()) {
                    estaOrdenado = false;
                    break;
                }
            }
            if (estaOrdenado) {
                Game Calidad = new Game("", "", 0, quality);
                int calidadBuscar = binarySearch(data, Calidad, Comparator.comparing(Game::getQuality));

                if (calidadBuscar < 0) {
                    calidadBuscar = -calidadBuscar - 1;
                }
                int mid = calidadBuscar;
                int izquierda = mid - 1;
                int derecha = mid + 1;
                gameCalidad.add(data.get(mid));

                while (izquierda >= 0 && data.get(izquierda).getQuality() == quality) {
                    gameCalidad.add(data.get(izquierda));
                    izquierda--;
                }

                while (derecha < data.size() && data.get(derecha).getQuality() == quality) {
                    gameCalidad.add(data.get(derecha));
                    derecha++;
                }
                return gameCalidad;
            } else {
                for (int i = 0; i < data.size(); i++) {
                    if (data.get(i).getQuality() == quality) {
                        gameCalidad.add(data.get(i));
                    }
                }
            }
            return gameCalidad;
        }

        public void sortByAlgorithm(String algorithm, String attribute) {
            Comparator<Game> comparador = null;
            int tamaño = data.size();

            if (attribute.equals("price")) {
                comparador = Comparator.comparing(Game::getPrice).reversed();
            } else if (attribute.equals("category")) {
                comparador = Comparator.comparing(Game::getCategory).reversed();
            } else if (attribute.equals("quality")) {
                comparador = Comparator.comparing(Game::getQuality).reversed();
            } else if (attribute.equals("")) {
                comparador = Comparator.comparing(Game::getPrice).reversed();
            }

            if (algorithm.equals("")) {
                Collections.sort(data, comparador);
            } else if (algorithm.equals("bubbleSort")) {
                for (int i = 0; i < tamaño - 1; i++) {
                    for (int j = 0; j < tamaño - i - 1; j++) {
                        if (comparador.compare(data.get(j), data.get(j + 1)) > 0) {
                            Game temp = data.get(j);
                            data.set(j, data.get(j + 1));
                            data.set(j + 1, temp);
                        }
                    }
                }
            } else if (algorithm.equals("insertionSort")) {
                for (int i = 0; i < tamaño; i++) {
                    Game key = data.get(i);
                    int j = i - 1;
                    while (j >= 0 && comparador.compare(data.get(j), key) > 0) {
                        data.set(j + 1, data.get(j));
                        j--;
                    }
                    data.set(j + 1, key);
                }
                this.sortedByAttribute = attribute;
            } else if (algorithm.equals("selectionSort")) {
                int min = -1;
                for (int i = 0; i < tamaño; i++) {
                    min = i;
                    for (int j = i + 1; j < tamaño; j++) {
                        if (comparador.compare(data.get(j), data.get(min)) < 0) {
                            min = j;
                        }
                    }
                    if (min != i) {
                        Game temp = data.get(i);
                        data.set(i, data.get(min));
                        data.set(min, temp);
                    }
                }
                this.sortedByAttribute = attribute;
            } else if (algorithm.equals("MergeSort")) {
                data = mergeSort(data, comparador);
                this.sortedByAttribute = attribute;
            } else if (algorithm.equals("QuickSort")) {
                quickSort(data, 0, data.size() - 1, comparador);
                this.sortedByAttribute = attribute;
            }
            this.sortedByAttribute = attribute;
        }

        // metodos para MergeSort y QuickSort
        private ArrayList<Game> mergeSort(ArrayList<Game> lista, Comparator<Game> comparador) {
            if (lista.size() <= 1) {
                return lista;
            }
            int mid = lista.size() / 2;
            ArrayList<Game> left = new ArrayList<>(lista.subList(0, mid));
            ArrayList<Game> right = new ArrayList<>(lista.subList(mid, lista.size()));
            left = mergeSort(left, comparador);
            right = mergeSort(right, comparador);

            return merge(left, right, comparador);
        }

        private ArrayList<Game> merge(ArrayList<Game> left, ArrayList<Game> right, Comparator<Game> comparador) {
            ArrayList<Game> resultado = new ArrayList<>();
            int i = 0;
            int j = 0;
            while (i < left.size() && j < right.size()) {
                if (comparador.compare(left.get(i), right.get(j)) <= 0) {
                    resultado.add(left.get(i++));
                } else {
                    resultado.add(right.get(j++));
                }
            }
            while (i < left.size()) {
                resultado.add(left.get(i++));
            }
            while (j < right.size()) {
                resultado.add(right.get(j++));
            }
            return resultado;
        }

        private void quickSort(ArrayList<Game> lista, int low, int hight, Comparator<Game> comparador) {
            if (low < hight) {
                int pivote = particion(lista, low, hight, comparador);
                quickSort(lista, low, pivote - 1, comparador);
                quickSort(lista, pivote + 1, hight, comparador);
            }
        }

        private int particion(ArrayList<Game> lista, int low, int high, Comparator<Game> comparador) {
            Game pivote = lista.get(high);
            int i = low - 1;

            for (int j = low; j < high; j++) {
                if (comparador.compare(lista.get(j), pivote) <= 0) {
                    i++;
                    Game temp = lista.get(i);
                    lista.set(i, lista.get(j));
                    lista.set(j, temp);
                }
            }
            Game temp = lista.get(i + 1);
            lista.set(i + 1, lista.get(high));
            lista.set(high, temp);
            return i + 1;
        }
        public Dataset copiar() {
            ArrayList<Game> copiaDatos = new ArrayList<>();
            for (Game g : this.data) {
                copiaDatos.add(new Game(g.getName(), g.getCategory(), g.getPrice(), g.getQuality()));
            }
            return new Dataset(copiaDatos);
        }
        public ArrayList<Game> getData() {
            return data;
        }
        private Comparator<Game> getComparator(String atributo) {
            return switch (atributo) {
                case "price" -> Comparator.comparingInt(Game::getPrice);
                case "quality" -> Comparator.comparingInt(Game::getQuality);
                case "category" -> Comparator.comparing(Game::getCategory);
                default -> null;
            };
        }
        public void countingSortByQuality() {
            ArrayList<Game> original = new ArrayList<>(this.data);
            int maxQuality = 100;

            // Crear los contenedores
            List<List<Game>> count = new ArrayList<>(maxQuality + 1);
            for (int i = 0; i <= maxQuality; i++) {
                count.add(new ArrayList<>());
            }

            // Distribuir los juegos según su calidad
            for (Game g : original) {
                count.get(g.getQuality()).add(g);
            }

            // Reconstruir la lista ordenada
            this.data.clear();
            for (List<Game> games : count) {
                this.data.addAll(games);
            }
        }

    }
    public static class GenerateData {

        private static final String[] palabras = {
                "Dragon", "Empire", "Quest", "Galaxy", "Legends", "Warrior",
                "Futbol", "Fight", "Geometry", "Hunt", "Gladiador", "Ball", "Tower"
        };

        private static final String[] categorias = {
                "Acción", "Aventura", "Estrategia", "RPG", "Deportes", "Simulación", "Romance", "Peleas", "AAA"
        };

        private static final int precioMin = 1000;
        private static final int precioMax = 70000;

        private static final int qualityMin = 0;
        private static final int qualityMax = 100;

        private final Random random = new Random();

        private String generarNombre() {
            return palabras[random.nextInt(palabras.length)] + palabras[random.nextInt(palabras.length)];
        }

        private String generarCategoria() {
            return categorias[random.nextInt(categorias.length)];
        }

        private int generarPrecio() {
            return precioMin + random.nextInt(precioMax - precioMin + 1);
        }

        private int generarQuality() {
            return qualityMin + random.nextInt(qualityMax - qualityMin + 1);
        }

        public ArrayList<Game> generarJuegos(int N) {
            ArrayList<Game> lista = new ArrayList<>(N);
            for (int i = 0; i < N; i++) {
                lista.add(new Game(
                        generarNombre(),
                        generarCategoria(),
                        generarPrecio(),
                        generarQuality()
                ));
            }
            return lista;
        }

        public void guardarCSV(ArrayList<Game> juegos, String filename) {
            try (FileWriter writer = new FileWriter(filename)) {
                writer.write("Name,Category,Price,Quality\n");
                for (Game g : juegos) {
                    writer.write(String.format("%s,%s,%d,%d\n",
                            g.getName(), g.getCategory(), g.getPrice(), g.getQuality()));
                }
                System.out.println("Archivo guardado: " + filename);
            } catch (IOException e) {
                System.err.println("Error al guardar archivo " + filename + ": " + e.getMessage());
            }
        }

        public void generarYGuardarConjuntos() {
            int[] tamaños = {100, 10000, 1000000};

            for (int tamaño : tamaños) {
                System.out.println("Generando dataset de tamaño: " + tamaño);
                ArrayList<Game> juegos = generarJuegos(tamaño);
                guardarCSV(juegos, "dataset_" + tamaño + ".csv");
            }
        }

        public static void main(String[] args) {
            new GenerateData().generarYGuardarConjuntos();
        }
    }
    public static class Benchmark {

        private static final int repeticiones = 3;
        private static final long tiempoMaximo = 300_000;

        public static void medirOrdenamientos(Dataset datasetOriginal, String atributo) {
            String[] algoritmos = {
                    "bubbleSort", "insertionSort", "selectionSort",
                    "mergeSort", "quickSort", "collectionsSort"
            };

            System.out.println("Benchmark para atributo: " + atributo);
            System.out.printf("%-15s %10s\n", "Algoritmo", "Tiempo(ms)");

            for (String algoritmo : algoritmos) {
                long sumaTiempos = 0;
                boolean excedido = false;

                for (int i = 0; i < repeticiones; i++) {
                    Dataset copia = datasetOriginal.copiar();

                    long inicio = System.currentTimeMillis();

                    switch (algoritmo) {
                        case "bubbleSort":
                            copia.sortByAlgorithm("bubbleSort", atributo);
                            break;
                        case "insertionSort":
                            copia.sortByAlgorithm("insertionSort", atributo);
                            break;
                        case "selectionSort":
                            copia.sortByAlgorithm("selectionSort", atributo);
                            break;
                        case "mergeSort":
                            copia.sortByAlgorithm("mergeSort", atributo);
                            break;
                        case "quickSort":
                            copia.sortByAlgorithm("quickSort", atributo);
                            break;
                        case "collectionsSort":
                            copia.sortByAlgorithm("", atributo);
                            break;
                    }

                    long fin = System.currentTimeMillis();
                    long duracion = fin - inicio;

                    if (duracion > tiempoMaximo) {
                        excedido = true;
                        break;
                    }

                    sumaTiempos += duracion;
                }

                if (excedido) {
                    System.out.printf("%-15s %10s\n", algoritmo, ">300000");
                } else {
                    long promedio = sumaTiempos / repeticiones;
                    System.out.printf("%-15s %10d\n", algoritmo, promedio);
                }
            }
            System.out.println();
        }
        public static void medirBusquedaPorPrecio(Dataset dataset, int precio) {
            medirTiempoBusqueda(() -> dataset.getGamesByPrice(precio), "búsqueda por precio");
        }

        public static void medirBusquedaPorRango(Dataset dataset, int min, int max) {
            medirTiempoBusqueda(() -> dataset.getGamesByPriceRange(min, max), "búsqueda por rango de precio");
        }

        public static void medirBusquedaPorCategoria(Dataset dataset, String categoria) {
            medirTiempoBusqueda(() -> dataset.getGamesByCategory(categoria), "búsqueda por categoría");
        }

        public static void medirBusquedaPorCalidad(Dataset dataset, int calidad) {
            medirTiempoBusqueda(() -> dataset.getGamesByQuality(calidad), "búsqueda por calidad");
        }

        private static void medirTiempoBusqueda(Runnable accion, String descripcion) {
            final int REPETICIONES = 3;
            long suma = 0;

            for (int i = 0; i < REPETICIONES; i++) {
                long inicio = System.currentTimeMillis();
                accion.run();
                long fin = System.currentTimeMillis();
                suma += (fin - inicio);
            }

            long promedio = suma / REPETICIONES;
            System.out.println("Tiempo promedio para " + descripcion + ": " + promedio + " ms");
        }

        public static void medirOrdenamientoIndividual(Dataset datasetOriginal, String algoritmo, String atributo) {
            final int repeticiones = 3;
            final long tiempoMaximo = 300_000;

            long sumaTiempos = 0;
            boolean excedido = false;

            for (int i = 0; i < repeticiones; i++) {
                Dataset copia = datasetOriginal.copiar();
                long inicio = System.currentTimeMillis();

                copia.sortByAlgorithm(algoritmo, atributo);

                long fin = System.currentTimeMillis();
                long duracion = fin - inicio;

                if (duracion > tiempoMaximo) {
                    excedido = true;
                    break;
                }

                sumaTiempos += duracion;
            }

            if (excedido) {
                System.out.printf("Algoritmo %s excedió el tiempo máximo de 300000 ms\n", algoritmo);
            } else {
                long promedio = sumaTiempos / repeticiones;
                System.out.printf("Tiempo promedio usando %s: %d ms\n", algoritmo, promedio);
            }
        }
        public static void mostrarTiempoBusqueda(String tipo, long inicio, long fin) {
            long duracion = fin - inicio;
            System.out.println("Tiempo de " + tipo + ": " + duracion + " ms");
        }
        public static void medirCountingSort(Dataset datasetOriginal) {
            System.out.println("Benchmark: Counting Sort por calidad");

            long sumaTiempos = 0;
            final int repeticiones = 3;

            for (int i = 0; i < repeticiones; i++) {
                Dataset copia = datasetOriginal.copiar();

                long inicio = System.currentTimeMillis();
                copia.countingSortByQuality();
                long fin = System.currentTimeMillis();

                sumaTiempos += (fin - inicio);
            }

            long promedio = sumaTiempos / repeticiones;
            System.out.println("Tiempo promedio (ms): " + promedio);
        }
    }
}