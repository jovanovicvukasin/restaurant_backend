INSERT INTO users (id, name, surname, email, password, phone, address, role) VALUES (1, 'Vukasin', 'Jovanovic', 'vukasin@example.com', '$2a$04$Cz090GgKrM3.kFHsKFpYLOyI.fdk2RwVLCqNsGg0CMwbTt1SEOtWa', '0651234567', 'Ulica 7, Novi Sad', 'ADMIN');
INSERT INTO users (id, name, surname, email, password, phone, address, role) VALUES (2, 'Petar', 'Petrovic', 'petar@example.com', '$2a$04$Cz090GgKrM3.kFHsKFpYLOyI.fdk2RwVLCqNsGg0CMwbTt1SEOtWa', '0631234557', 'Ulica 10, Novi Sad', 'CUSTOMER');
INSERT INTO users (id, name, surname, email, password, phone, address, role) VALUES (3, 'Ana', 'Anic', 'ana@example.com', '$2a$04$Cz090GgKrM3.kFHsKFpYLOyI.fdk2RwVLCqNsGg0CMwbTt1SEOtWa', '0641234566', 'Ulica 3, Novi Sad', 'CUSTOMER');

-- password: 12345 (bcrypt hash)

INSERT INTO restaurant_table (id, table_number, seats) VALUES (1, 10, 5);
INSERT INTO restaurant_table (id, table_number, seats) VALUES (2, 11, 10);
INSERT INTO restaurant_table (id, table_number, seats) VALUES (3, 12, 4);
INSERT INTO restaurant_table (id, table_number, seats) VALUES (4, 13, 4);
INSERT INTO restaurant_table (id, table_number, seats) VALUES (5, 14, 2);

INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (1, 'Pasta Carbonara', 'Kremasta italijanska pasta sa slaninom', 'PASTA', '/uploads/carbonara.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (2, 'Pizza Capricciosa', 'Pizza sa šunkom, pečurkama i sirom', 'PIZZA', '/uploads/capricciosa.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (3, 'Cezar salata', 'Salata sa piletinom, parmezanom i dresingom', 'SALAD', '/uploads/cezar.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (4, 'Coca cola', 'Osvežavajuće bezalkoholno piće, 330ml', 'DRINK', '/uploads/cola.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (5, 'Tiramisu', 'Kremasti italijanski desert sa kafom i maskarponeom', 'DESSERT', '/uploads/tiramisu.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (6, 'Pizza Margherita', 'Pizza sa paradajz pelatom i sirom', 'PIZZA', '/uploads/margherita.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (7, 'Rosa voda', 'Negazirana voda, 500ml', 'DRINK', '/uploads/rosa.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (8, 'Spaghetti Bolognese', 'Špagete u sosu od paradajza i mlevenog mesa', 'PASTA', '/uploads/bolognese.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (9, 'Čokoladni sufle', 'Topli kolač od čokolade sa kuglom sladoleda', 'DESSERT', '/uploads/sufle.jpg');
INSERT INTO menu_items (id, name, description, item_category, image_url) VALUES (10, 'Lasagna Bolognese', 'Zapečene lazanje sa mlevenim mesom i sirom', 'PASTA', '/uploads/lazagna.jpg');

INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (1, 1100.99, 'RSD', '2025-10-01', '2026-01-01', 1);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (2, 1250.00, 'RSD', '2025-10-01', '2026-01-01', 2);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (3, 970.00, 'RSD', '2025-10-01', '2026-01-01', 3);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (4, 210.00, 'RSD', '2025-10-01', '2026-01-01', 4);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (5, 550.00, 'RSD', '2025-10-01', '2026-01-01', 5);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (6, 1000.99, 'RSD', '2025-10-01', '2026-01-01', 6);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (7, 180.00, 'RSD', '2025-10-01', '2026-01-01', 7);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (8, 1200.00, 'RSD', '2025-10-01', '2026-01-01', 8);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (9, 700.00, 'RSD', '2025-10-01', '2026-01-01', 9);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (10, 1300.00, 'RSD', '2025-10-01', '2026-01-01', 10);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (11, 999.00, 'RSD', '2025-06-01', '2025-09-30', 1);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (12, 1100.00, 'RSD', '2025-01-01', '2025-09-30', 2);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (13, 899.99, 'RSD', '2025-01-01', '2025-09-30', 3);
INSERT INTO price_items (id, price, currency, valid_from, valid_to, menu_item_id) VALUES (14, 901.00, 'RSD', '2025-01-01', '2025-05-31', 1);


INSERT INTO table_reservations (id, start_time, end_time, request_status, number_of_guests, user_id, table_id) VALUES (1, '2025-10-20 18:00:00', '2025-10-20 20:00:00', 'ACCEPTED', 2, 2, 1);
INSERT INTO table_reservations (id, start_time, end_time, request_status, number_of_guests, user_id, table_id) VALUES (2, '2025-10-21 19:30:00', '2025-10-21 21:30:00', 'CANCELED', 4, 3, 2);
INSERT INTO table_reservations (id, start_time, end_time, request_status, number_of_guests, user_id, table_id) VALUES (3, '2025-10-19 17:00:00', '2025-10-20 19:00:00', 'ACCEPTED', 4, 2, 3);


INSERT INTO orders (id, order_date_time, order_status, order_type, total_amount, user_id) VALUES (1, '2025-10-18T18:30:00', 'ACCEPTED', 'DELIVERY', 2420.99, 2);
INSERT INTO orders (id, order_date_time, order_status, order_type, total_amount, user_id) VALUES (2, '2025-10-18T19:10:00', 'ACCEPTED', 'PICKUP', 2500.00, 3);
INSERT INTO orders (id, order_date_time, order_status, order_type, total_amount, user_id) VALUES (3, '2025-10-18T19:10:00', 'CANCELED', 'PICKUP', 1250.00, 2);


INSERT INTO order_items (id, quantity, total_price, menu_item_id, order_id) VALUES (1, 1, 1100.99, 1, 1);
INSERT INTO order_items (id, quantity, total_price, menu_item_id, order_id) VALUES (2, 1, 970.00, 3, 1);
INSERT INTO order_items (id, quantity, total_price, menu_item_id, order_id) VALUES (3, 2, 2500.00, 2, 2);
INSERT INTO order_items (id, quantity, total_price, menu_item_id, order_id) VALUES (4, 1, 1250.00, 2, 3);









