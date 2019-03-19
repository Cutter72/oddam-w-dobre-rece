INSERT INTO `oddam`.`role` (`role_id`, `role`) VALUES
('1', 'ROLE_ADMIN'),
('2', 'ROLE_USER');

-- Init first admin, email= 'qwe@qwe' pssword = 'qwe'
INSERT INTO `oddam`.`users` (`id`, `email`, `enabled`, `first_name`, `last_name`, `password`) VALUES
('1', 'qwe@qwe', '1', 'qwe', 'qwe', '$2a$10$rfENR7ab5VWoMjah/q5R4.M0uB5Jg66vzqaai318x374VCe0ZsJc6');
INSERT INTO `oddam`.`user_role` (`user_id`, `role_id`) VALUES
('1', '1');

INSERT INTO `oddam`.`organization_type` (`id`, `type`) VALUES
('1', 'Fundacja'),
('2', 'Organizacja pozarządowa'),
('3', 'Lokalna zbiórka');

INSERT INTO `oddam`.`organization_target` (`id`, `target`) VALUES
('1', 'dzieci'),
('2', 'samotne matki'),
('3', 'bezdomni'),
('4', 'niepełnosprawni'),
('5', 'osoby starsze');

INSERT INTO `oddam`.`organization_need` (`id`, `need`) VALUES
('1', 'ubrania dla dorosłych'),
('2', 'ubrania dla dzieci'),
('3', 'jedzenie'),
('4', 'sprzęt AGD'),
('5', 'meble'),
('6', 'zabawki'),
('7', 'ciepłe koce'),
('8', 'książki'),
('9', 'ubrania do wyrzucenia'),
('10', 'inne');

INSERT INTO `oddam`.`organization` (`id`, `mission`, `name`, `organization_type_id`, `city_id`) VALUES
('1', 'Pomoc dzieciom z ubogich rodzin.', 'Dbam o Zdrowie', '1', '1'),
('2', 'Pomoc osobom znajdującym się w trudnej sytuacji życiowej.', 'Dla dzieci', '1', '2'),
('3', 'Pomoc dla osób nie posiadających miejsca zamieszkania.', 'Bez domu', '1', '3');

INSERT INTO `oddam`.`organization_organization_target` (`organization_id`, `organization_target_id`) VALUES
('1', '1');

INSERT INTO `oddam`.`organization_organization_need` (`organization_id`, `organization_need_id`) VALUES
('1', '2'),
('1', '3'),
('1', '4'),
('1', '5'),
('1', '6'),
('2', '2'),
('2', '5'),
('2', '6'),
('3', '1'),
('3', '3'),
('3', '7');

INSERT INTO `oddam`.`city` (`id`, `city`) VALUES
('1', 'Gdańsk'),
('2', 'Kraków'),
('3', 'Poznań'),
('4', 'Warsszawa');
