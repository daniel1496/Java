insert into books (title, author) values
    ('1984', 'Orwell'),
    ('Brave New World', 'Huxley'),
    ('On Liberty', 'Mill'),
    ('Rasselas', 'Johnson');

insert into reviews (reviewer, content, rating, book_id) values
    ('Stuart', 'It was OK, though I did not find it as prophetic as some claim', 'THREE_STARS', 1),
    ('Stuart', 'It was better than 1984', 'THREE_STARS', 2),
    ('Stuart', 'A difficult read given it was written in the 17th century but worth the effort', 'FOUR_STARS', 3),
    ('Stuart', 'Excellent - truly insightful', 'FIVE_STARS', 4),
    ('Dave', 'Terrible, I could not understand a word of it', 'ONE_STAR', 1),
    ('Sarah', 'Incredible', 'FIVE_STARS', 1),
    ('Jane', 'It changed my life', 'FIVE_STARS', 1);