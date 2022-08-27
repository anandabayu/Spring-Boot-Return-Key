INSERT INTO public.item (sku, name, price) VALUES ('MENS-156', 'Small Black T-Shirt', 50);
INSERT INTO public.item (sku, name, price) VALUES ('NIKE-7', 'Nike Air Jordans - Size 7', 110.75);
INSERT INTO public.item (sku, name, price) VALUES ('SOC-1', 'Soccer Ball', 25.99);
INSERT INTO public.item (sku, name, price) VALUES ('MU-5091', 'Lip Gloss', 15.25);
INSERT INTO public.item (sku, name, price) VALUES ('NIKE-56', 'Nike Medium Red Leggings', 75.5);
INSERT INTO public.item (sku, name, price) VALUES ('MU-4129', 'Eye Shadow', 22.85);
INSERT INTO public.item (sku, name, price) VALUES ('PAR-14', 'Chanel - CHANCE EAU FRAÎCHE Eau de Toilette', 149.99);
INSERT INTO public.item (sku, name, price) VALUES ('NIKE-143', 'Nike Mercurial Superfly 8 Elite FG Firm Ground Soccer Cleat', 249.99);

INSERT INTO public."order" (id, email) VALUES ('RK-478', 'john@example.com');
INSERT INTO public."order" (id, email) VALUES ('RK-642', 'will@example.com');
INSERT INTO public."order" (id, email) VALUES ('RK-238', 'carly@example.com');
INSERT INTO public."order" (id, email) VALUES ('RK-912', 'karen@example.com');
INSERT INTO public."order" (id, email) VALUES ('RK-239', 'steve@example.com');
INSERT INTO public."order" (id, email) VALUES ('RK-149', 'dalton@example.com');

INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (1, 2, 'RK-478', 'MENS-156');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (2, 1, 'RK-478', 'NIKE-7');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (3, 2, 'RK-642', 'SOC-1');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (10, 1, 'RK-149', 'SOC-1');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (11, 1, 'RK-149', 'NIKE-143');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (6, 2, 'RK-238', 'MU-4129');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (4, 3, 'RK-238', 'MU-5091');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (5, 1, 'RK-238', 'NIKE-56');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (8, 1, 'RK-912', 'PAR-14');
INSERT INTO public.order_item (id, qty, order_id, item_id) VALUES (9, 1, 'RK-239', 'MENS-156');