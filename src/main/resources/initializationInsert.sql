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