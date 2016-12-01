
INSERT INTO Browser(ID, Name) VALUES (1,'Chrome');

INSERT INTO Referer(ID, Name) VALUES (1,'www.test.com');

INSERT INTO Country(ID, Name) VALUES (1,'test_country');

INSERT INTO users(ID, Name, Password, Registered_At,Email) VALUES (1,'test','test',null);

INSERT INTO urls(ID, URL, Created_At, User_ID) VALUES (1,'www.test.com',null,1);

INSERT INTO url_visit(ID, URL, Clicked_At, Browser, Location, Referer) VALUES (1,1,null,1,1,1);


