delete from BlogEntity;

insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (0, 'Kill', 'Me', 'Now', 'Please', 79);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (1, 'Free', 'Hong', 'Kong', 'Please', 69);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (2, 'What', 'A', 'Fucking', 'Nigg', 59);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (6, 'Guaid', 'Pipo', 'Japi', 'Clap', 49);

update BlogEntity set siguiente_id = 1 where id = 0;
update BlogEntity set siguiente_id = 2 where id = 1;
update BlogEntity set anterior_id = 2 where id = 6;
update BlogEntity set anterior_id = 6 where id = 0;