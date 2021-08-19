#!/bin/sh

# Borg backup script
# Mount destination, performe backup, and unmount destination again

echo "Installierte Pakete, Stand:" $(date) > ~/pacman-Qs
echo "" >> ~/pacman-Qs
pacman -Qs | grep "local\/" | cut -d / -f 2 >> ~/pacman-Qs

sudo mount.cifs //192.168.0.213/backup_x230 /mnt/backup -o credentials=/root/nas-exlumine.crd,uid=1000

borg create -s /mnt/backup/x230::x230-exlumine-{now:%Y-%m-%d_%H:%M:%S} ~/ \
	--exclude '~/.bitmonero/' \
	--exclude '~/game/' \
	--exclude '~/.local/share/Steam/' \
	--exclude '~/audio/NAS/' \
	--exclude '~/books/NAS/' \
	--exclude '~/picture/NAS/' \
	--exclude '~/video/NAS/'
#	--exclude ''

sync

sudo umount -v /mnt/backup
