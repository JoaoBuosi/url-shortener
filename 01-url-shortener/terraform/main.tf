data "aws_ami" "ubuntu" { most_recent = true; owners = ["099720109477"]; filter { name = "name"; values = ["ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"] } }
resource "aws_security_group" "app" {
 name = "url-shortener-sg"
 ingress { from_port=22; to_port=22; protocol="tcp"; cidr_blocks=[var.ssh_cidr] }
 ingress { from_port=8081; to_port=8081; protocol="tcp"; cidr_blocks=[var.ssh_cidr] }
 egress { from_port=0; to_port=0; protocol="-1"; cidr_blocks=["0.0.0.0/0"] }
}
resource "aws_instance" "app" {
 ami=data.aws_ami.ubuntu.id; instance_type=var.instance_type; key_name=var.key_name; vpc_security_group_ids=[aws_security_group.app.id]
 user_data = <<-EOF
 #!/bin/bash
 apt-get update -y
 apt-get install -y docker.io docker-compose-v2 git
 systemctl enable --now docker
 EOF
 tags={Name="url-shortener-demo"}
}
output "instance_public_ip" { value=aws_instance.app.public_ip }
