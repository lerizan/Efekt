# Efekt Plugin

Minecraft sunucunuz için geliştirilmiş oyuncuların belirli miktarda blok kırdıktan sonra özel efektler almasını sağlayan bir plugin.

## Komutlar

### Admin Komutları

| Komut | Açıklama | Yetki |
|-------|----------|-------|
| `/efekt ver <oyuncu>` | Belirtilen oyuncuya efektleri verir (1200 blok kırmış gibi) | `efekt.admin` |
| `/efekt sıfırla <oyuncu>` | Belirtilen oyuncunun ilerlemesini sıfırlar | `efekt.admin` |
| `/efekt reload` | Config dosyasını yeniden yükler | `efekt.admin` |

**Örnek Kullanım:**
```
/efekt ver Lerizan
/efekt sıfırla Oyuncu123
/efekt reload
```

## Config Ayarları

Config dosyası (`plugins/Efekt/config.yml`) aşağıdaki ayarları içerir:

### Temel Ayarlar

```yaml
gerekli-bloklar: 1200  # Kaç blok kırılması gerektiğini belirler
```

### Efektler

```yaml
efektler:
  - tip: STRENGTH      # Efekt tipi (STRENGTH, SPEED, HASTE, vb.)
    seviye: 2          # Efekt seviyesi
```

**Mevcut Efekt Tipleri:**
- `STRENGTH` - Kuvvet
- `SPEED` - Hız
- `HASTE` - Acele
- `REGENERATION` - Yenilenme
- `FIRE_RESISTANCE` - Ateş Direnci
- Ve daha fazlası...

### Mesajlar

```yaml
mesajlar:
  ilerleme: "&eBüyüleri almak için blok kır! &7{bloklar}/{gerekli}"
  aktif-edildi: "&aBüyüler aktif edildi"
  title-baslik: "&aBaşarılı!"
  title-alt-baslik: "&7Sonunda {gerekli} blok kırdığın için büyüleri alabiliceksin"
  respawn-title-baslik: "&eBüyü"
  respawn-title-alt-baslik: "&cÖldüğün için büyüleri tekrar aldın"
```

### Sesler

```yaml
sesler:
  efekt-alindi: "ENTITY_PLAYER_LEVELUP"  # Efekt verildiğinde çalacak ses
  ses-seviyesi: 1.0                      # Ses seviyesi (0.0 - 1.0)
```

### Ayarlar

```yaml
ayarlar:
  actionbar-goster: true              # ActionBar gösterimini aç/kapat
  actionbar-sonsuz-goster: true       # ActionBar'ı sürekli göster
  belirli-bloklar-say: false          # Sadece belirli blokları say
  sayilacak-bloklar: []               # Sayılacak blok listesi (belirli-bloklar-say: true ise)
```

## Nasıl Çalışır?

1. Oyuncular sunucuda blok kırmaya başlar
2. Her blok kırıldığında ilerleme kaydedilir
3. ActionBarda oyuncular ilerlemelerini görür: `Büyüleri almak için blok kır! 500/1200`
4. Belirlenen miktarda blok kırıldığında:
   - Efektler otomatik olarak verilir
   - Title mesajı gösterilir
   - Ses çalar
   - Veriler veritabanına kaydedilir
5. Oyuncu öldüğünde veya totem patladığında efektler otomatik geri verilir

## Yetkiler

| Yetki | Açıklama |
|-------|----------|
| `efekt.admin` | Admin komutlarını kullanma yetkisi |

**Yetki Verme Örneği (LuckPerms):**
```
/lp user OyuncuAdı permission set efekt.admin
```

**Veriler kayboluyor:**
- `plugins/Efekt/data.db` dosyasının silinmediğinden emin olun
- Veritabanı dosyasına yazma izni olduğundan emin olun

## Destek

Herhangi bir sorun yaşarsanız:
- Discord: `lerizan`
- Discord Sunucusu: https://discord.gg/qrrGnkvdMB


