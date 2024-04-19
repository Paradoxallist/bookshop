INSERT INTO categories (id, name, description)
VALUES (1, 'Fantasy', 'Adventures of Magic and Sword');

INSERT INTO books (id, title, author, price, isbn)
VALUES (1, 'Dark world', 'Jhon Fordon', 500, '978-1-2365-678-9-0');

INSERT INTO books_categories (book_id, category_id) VALUES (1, 1);
