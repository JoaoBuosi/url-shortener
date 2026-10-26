variable "aws_region" { type = string; default = "us-east-1" }
variable "instance_type" { type = string; default = "t3.micro" }
variable "ssh_cidr" { type = string; description = "Seu IP público em CIDR /32; não use 0.0.0.0/0"; }
variable "key_name" { type = string; description = "Nome de uma key pair existente na AWS"; }
