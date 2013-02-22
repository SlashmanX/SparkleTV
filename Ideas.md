Process to check for new episodes
==================================

1. Scan TVDb/TVRage for new episodes
2. Search TPB for torrent
3. Put torrent in 'Incoming Torrents'
4. When that's finished it goes into 'Unsorted Downloads'
5. Watch 'Unsorted downloads' for new files
6. On new file, parse name, rename & move


Database
====================
* A show can have many episodes
* An episode can only have 1 show

Show
-------
* Show Name
* Show ID (tvdb/tvrage id)
* Latest aired episode # (foreign key episodes table)
* Air Day
* Air Time
* Still running
* Genre(s)
* Runtime
* Summary
* Total Seasons

Episode
--------
* Episode id
* Show ID (foreign key)
* AirDate
* Season #
* Episode #
* Name
* Summary
* hasBeenDownloaded

Schedule
======================
* Save episode info in separate table
* Allow user to view a schedule of upcoming episodes
* Allow user to refresh the episode schedule

Application
======================
* 2 services
  * 1 to run on boot (or on request) to check for new episodes
  * 1 to watch the 'Unsorted Downloads' folder for new items
* List of Show Artwork similar to seriesguide-chrome
* Long press options:
  * Play latest episode (opens video player)
  * Manual check for new episodes
  * Delete from favourites

Useful Links
====================
* [Java TVDb API](https://github.com/Omertron/api-thetvdb)
* [Java TVRage API](https://github.com/Omertron/api-tvrage)
* [Download torrents from TPB](http://www.geniouspc.com/2012/03/trick-how-to-download-pirate-bay.html)
* [isoHunt JSON Search (includes direct link to .torrent)](http://isohunt.com/js/json.php?ihq=castle.s05e01&sort=seeds)
* [TvNamer](https://github.com/dbr/tvnamer) - Useful to check out the parser that's used for filenames
