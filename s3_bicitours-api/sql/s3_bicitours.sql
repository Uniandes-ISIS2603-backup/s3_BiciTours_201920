delete from BlogEntity;

insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (0, 'Kill', 'Me', 'Now', 'Please', 79);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (1, 'Free', 'Hong', 'Kong', 'Please', 69);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (2, 'What', 'A', 'Fucking', 'Nigg', 59);
insert into BlogEntity (id, rutaImagen, rutaVideo, texto, titulo, calificacionPromedio) values (6, 'Guaid', 'Pipo', 'Japi', 'Clap', 49);

update BlogEntity set siguiente_id = 1 where id = 0;
update BlogEntity set siguiente_id = 2 where id = 1;
update BlogEntity set anterior_id = 2 where id = 6;
update BlogEntity set anterior_id = 6 where id = 0;

 insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (0,'Seguro contra robo de bicicletas','El robo de debe reportar 24 horas despues de sucedido para hacer valido el seguro', 'Antirobo');
 insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (1,'Seguro para fallecimiento','Se hace efectivo si es cliente fallece en un accidente durante el tour', 'Seguro de vida');
 insert into SEGUROENTITY (ID,CARACTERISTICAS,CONDICIONES,TIPO) VALUES (2,'Seguro para lesiones','Se cubren todos los gastos medicos que requiera el participante tras sufrir una lesion en el tour', 'Seguro de salud');