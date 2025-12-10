drop database `e-commerce`;

create database if not exists `e-commerce`;

use `e-commerce`;

-- ======================================================
-- USERS
-- ======================================================
create table user(
                     id bigint primary key auto_increment,
                     email varchar(255) not null unique,
                     first_name varchar(100),
                     last_name varchar(100),
                     phone varchar(20),
                     password varchar(255),
                     role varchar(255),
                     created_at timestamp default current_timestamp,
                     last_login timestamp null
) ENGINE=InnoDB;

-- ======================================================
-- SELLERS
-- ======================================================
create table sellers(
                        id bigint primary key auto_increment,
                        user_id bigint not null,
                        seller_name varchar(255),
                        verified boolean default false,
                        created_at timestamp default current_timestamp,
                        payment_account_id varchar(255),
                        foreign key (user_id) references user(id) on delete cascade
) ENGINE=InnoDB;

-- ======================================================
-- CATEGORIES + Recursive relationship
-- ======================================================
create table category(
                         id bigint primary key auto_increment,
                         name varchar(255) not null ,
                         parent_category_id bigint null ,
                         foreign key (parent_category_id) references category(id) on delete set null
)ENGINE=InnoDB;

-- ======================================================
-- Brands
-- ======================================================
CREATE TABLE brand (
                       id bigint PRIMARY KEY auto_increment,
                       name VARCHAR(255) NOT NULL UNIQUE,
                       description TEXT,
                       country VARCHAR(100),
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ======================================================
-- PRODUCTS
-- ======================================================
create table product(
                        id bigint primary key auto_increment,
                        seller_id bigint not null ,
                        brand_id bigint NULL,
                        sku varchar(255),
                        title varchar(255),
                        description text ,
                        base_price decimal(10,2) not null,
                        active boolean default true,
                        attributes json,
                        created_at timestamp default current_timestamp,
                        foreign key (seller_id) references sellers(id) on delete cascade ,
                        foreign key (brand_id) references brand(id) on delete set null
)ENGINE=InnoDB;

-- ======================================================
-- Product Variants
-- ======================================================
create table product_variant(
                                id bigint primary key auto_increment,
                                product_id bigint not null ,
                                sku varchar(255),
                                attributes json,
                                price decimal(10,2) not null ,
                                weight_grams int,
                                stock_size int,
                                foreign key (product_id) references product(id) on delete cascade
)ENGINE=InnoDB;

-- ======================================================
-- Product images
-- ======================================================
create table product_image(
                              id bigint primary key auto_increment,
                              product_id bigint not null ,
                              url varchar(500) not null ,
                              alt_text varchar(255),
                              display_order int,
                              foreign key(product_id) references product(id)
)ENGINE=InnoDB;

-- ======================================================
-- PRODUCT-CATEGORY (M:N)
-- ======================================================
CREATE TABLE product_category (
                                  product_id bigint NOT NULL,
                                  category_id bigint NOT NULL,
                                  PRIMARY KEY (product_id, category_id),
                                  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE cascade ,
                                  FOREIGN KEY (category_id) REFERENCES category(id) ON DELETE cascade
) ENGINE=InnoDB;

-- ======================================================
-- ADDRESSES
-- ======================================================
CREATE TABLE address (
                         id bigint PRIMARY KEY auto_increment,
                         user_id bigint NOT NULL,
                         full_name VARCHAR(255),
                         line1 VARCHAR(255),
                         line2 VARCHAR(255),
                         city VARCHAR(100),
                         latitude decimal(12),
                         longitude decimal(12),
                         region VARCHAR(100),
                         postal_code VARCHAR(20),
                         country VARCHAR(100),
                         phone VARCHAR(20),
                         FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ======================================================
-- ORDERS
-- ======================================================
CREATE TABLE `order` (
                         id bigint PRIMARY KEY auto_increment,
                         user_id bigint NOT NULL,
                         status VARCHAR(50),
                         total_amount DECIMAL(10,2) NOT NULL,
                         currency CHAR(3) NOT NULL,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES user(id)
) ENGINE=InnoDB;

-- ======================================================
-- ORDER ITEMS
-- ======================================================
CREATE TABLE order_item (
                            id bigint PRIMARY KEY auto_increment,
                            order_id bigint not null ,
                            product_id bigint not null,
                            variant_id bigint , #has at least one variant that mirrors the original product
                            quantity INT NOT NULL,
                            unit_price DECIMAL(10,2) NOT NULL,
                            tax_amount DECIMAL(10,2) NOT NULL,
                            payment_dues bigint,
                            FOREIGN KEY (product_id) REFERENCES product(id),
                            FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
                            FOREIGN KEY (variant_id) REFERENCES product_variant(id)
) ENGINE=InnoDB;

-- ======================================================
-- PAYMENTS
-- ======================================================
CREATE TABLE payment (
                         id bigint auto_increment PRIMARY KEY,
                         order_id bigint NOT NULL,
                         provider VARCHAR(50),
                         provider_txn_id VARCHAR(255),
                         status VARCHAR(50),
                         amount DECIMAL(10,2) NOT NULL,
                         paid_at TIMESTAMP NULL,
                         FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ======================================================
-- SHIPMENTS (1:1 with Orders)
-- ======================================================
CREATE TABLE shipment (
                          id bigint primary key auto_increment,
                          order_id bigint not null ,
                          carrier VARCHAR(100),
                          tracking_number VARCHAR(255),
                          status VARCHAR(50),
                          shipped_at TIMESTAMP NULL,
                          delivered_at TIMESTAMP NULL,
                          address_id bigint,
                          FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
                          FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE SET NULL ON UPDATE CASCADE
)ENGINE=InnoDB;

-- ======================================================
-- REVIEWS
-- ======================================================
CREATE TABLE review (
                        id bigint PRIMARY KEY auto_increment,
                        product_id bigint NOT NULL,
                        user_id bigint NOT NULL,
                        rating INT NOT NULL,
                        title VARCHAR(255),
                        body TEXT,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
                        FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ======================================================
-- CARTS
-- ======================================================
CREATE TABLE cart (
                      id bigint PRIMARY KEY auto_increment,
                      user_id bigint NOT NULL,
                      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                      FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB;
-- ======================================================
-- CART ITEMS
-- ======================================================
CREATE TABLE cart_item (
                           id bigint PRIMARY KEY auto_increment,
                           cart_id bigint NULL,
                           product_id bigint ,
                           variant_id bigint,
                           quantity INT NOT NULL,
                           FOREIGN KEY (cart_id) REFERENCES cart(id) ON DELETE CASCADE,
                           FOREIGN KEY (product_id) REFERENCES product(id),
                           FOREIGN KEY (variant_id) REFERENCES product_variant(id)
) ENGINE=InnoDB;
-- ======================================================
-- WISHLISTS
-- ======================================================
CREATE TABLE Wishlists (
                           id bigint PRIMARY KEY auto_increment,
                           user_id bigint NOT NULL,
                           name VARCHAR(255) NOT NULL,
                           FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- ======================================================
-- WISHLIST ITEMS
-- ======================================================
CREATE TABLE WishlistItems (
                               id bigint PRIMARY KEY auto_increment,
                               wishlist_id bigint NOT NULL,
                               product_id bigint NOT NULL,
                               variant_id bigint,
                               FOREIGN KEY (wishlist_id) REFERENCES Wishlists(id) ON DELETE CASCADE,
                               FOREIGN KEY (product_id) REFERENCES product(id),
                               foreign key (variant_id) references product_variant(id)
) ENGINE=InnoDB;


CREATE TABLE brand_image (
                             id BIGINT PRIMARY KEY AUTO_INCREMENT,
                             brand_id BIGINT NOT NULL,
                             url VARCHAR(255) NOT NULL,

                             CONSTRAINT fk_brand_image_brand
                                 FOREIGN KEY (brand_id)
                                     REFERENCES brand(id)
                                     ON DELETE CASCADE
);

create table BlacklistedToken (
                                  id bigint primary key auto_increment,
                                  token varchar(500)
)Engine=InnoDB;

-- ===========================
-- USER
-- ===========================


CREATE INDEX idx_user_phone ON user(phone);
CREATE INDEX idx_user_role ON user(role);

-- ===========================
-- SELLERS
-- ===========================
CREATE INDEX idx_sellers_user_id ON sellers(user_id);
CREATE INDEX idx_sellers_verified ON sellers(verified);

-- ===========================
-- CATEGORY
-- ===========================
CREATE INDEX idx_category_parent ON category(parent_category_id);

-- ===========================
-- BRAND
-- ===========================
CREATE INDEX idx_brand_name ON brand(name);
CREATE INDEX idx_brand_country ON brand(country);

-- ===========================
-- PRODUCT
-- ===========================
CREATE INDEX idx_product_seller ON product(seller_id);
CREATE INDEX idx_product_brand ON product(brand_id);
CREATE INDEX idx_product_active ON product(active);
CREATE INDEX idx_product_sku ON product(sku);
CREATE FULLTEXT INDEX idx_product_title ON product(title);

-- ===========================
-- PRODUCT_VARIANT
-- ===========================
CREATE INDEX idx_variant_product ON product_variant(product_id);
CREATE INDEX idx_variant_sku ON product_variant(sku);

-- ===========================
-- PRODUCT_IMAGE
-- ===========================
CREATE INDEX idx_product_image_product ON product_image(product_id);

-- ===========================
-- PRODUCT_CATEGORY
-- ===========================
CREATE INDEX idx_product_category_category ON product_category(category_id);

-- ===========================
-- ADDRESS
-- ===========================
CREATE INDEX idx_address_user ON address(user_id);
CREATE INDEX idx_address_postal_code ON address(postal_code);
CREATE INDEX idx_address_city ON address(city);

-- ===========================
-- ORDER
-- ===========================
CREATE INDEX idx_order_user ON `order`(user_id);
CREATE INDEX idx_order_status ON `order`(status);
CREATE INDEX idx_order_created_at ON `order`(created_at);

-- ===========================
-- ORDER_ITEM
-- ===========================
CREATE INDEX idx_order_item_order ON order_item(order_id);
CREATE INDEX idx_order_item_product ON order_item(product_id);
CREATE INDEX idx_order_item_variant ON order_item(variant_id);

-- ===========================
-- PAYMENT
-- ===========================
CREATE INDEX idx_payment_order ON payment(order_id);
CREATE INDEX idx_payment_status ON payment(status);

-- ===========================
-- SHIPMENT
-- ===========================
CREATE INDEX idx_shipment_order ON shipment(order_id);
CREATE INDEX idx_shipment_status ON shipment(status);
CREATE INDEX idx_shipment_address ON shipment(address_id);

-- ===========================
-- REVIEW
-- ===========================
CREATE INDEX idx_review_product ON review(product_id);
CREATE INDEX idx_review_user ON review(user_id);
CREATE INDEX idx_review_rating ON review(rating);

-- ===========================
-- CART
-- ===========================
CREATE INDEX idx_cart_user ON cart(user_id);

-- ===========================
-- CART_ITEM
-- ===========================
CREATE INDEX idx_cart_item_cart ON cart_item(cart_id);
CREATE INDEX idx_cart_item_product ON cart_item(product_id);
CREATE INDEX idx_cart_item_variant ON cart_item(variant_id);

-- ===========================
-- WISHLISTS
-- ===========================
CREATE INDEX idx_wishlist_user ON wishlists(user_id);

-- ===========================
-- WISHLIST_ITEMS
-- ===========================
CREATE INDEX idx_wishlist_items_wishlist ON wishlist_items(wishlist_id);
CREATE INDEX idx_wishlist_items_product ON wishlist_items(product_id);
CREATE INDEX idx_wishlist_items_variant ON wishlist_items(variant_id);

-- ===========================
-- BRAND_IMAGE
-- ===========================
CREATE INDEX idx_brand_image_brand ON brand_image(brand_id);

-- ===========================
-- BLACKLISTED TOKEN
-- ===========================
CREATE UNIQUE INDEX idx_blacklisted_token ON BlacklistedToken(token);
