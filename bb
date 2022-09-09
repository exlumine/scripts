#!/bin/sh

# Borg backup script
# Mount destination, performe backup, and unmount destination again

# Test for mounted NAS, if so, exit
if [ "$(mount | grep -c cifs)" -ne 0 ]
then
	echo "Detected mounted NAS... exiting backup process."; exit;
fi

echo "Installierte Pakete, Stand:" $(date) > ~/pacman-Qe
echo "" >> ~/pacman-Qe
pacman -Qe >> ~/pacman-Qe

sudo mount.cifs //192.168.0.213/backup_x230 /mnt/backup -o credentials=/root/nas-exlumine.crd,uid=1000

borg create -s /mnt/backup/x230::x230-exlumine-{now:%Y-%m-%d_%H:%M:%S} ~/ \
	--exclude '~/.bitmonero/' \
	--exclude '~/game/' \
	--exclude '~/.local/share/Steam/' \
	--exclude '~/audio/NAS/' \
	--exclude '~/books/NAS/' \
	--exclude '~/picture/NAS/' \
	--exclude '~/video/NAS/'

sync

sudo umount -v /mnt/backup

notify-send "👽Borg" "Backup done."
