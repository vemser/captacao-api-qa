from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.wait import WebDriverWait
from discord_webhook import DiscordWebhook, DiscordEmbed
import re
import os
import time

webhook_key = os.getenv('DISCORD_WEBHOOK_URL')
base_url = os.getenv('REPORT_URL') + "/" + os.getenv('RUN_NUMBER') + '/index.html'
title = os.getenv('PIPELINE_NAME')

options = webdriver.ChromeOptions()
options.add_argument('--headless')
driver = webdriver.Chrome(options=options)
driver.set_window_size(1920, 1080)

time.sleep(60)

driver.get(base_url)

time.sleep(5)


def getElement(selector, timeout=10):
    try:
        return WebDriverWait(driver, timeout).until(EC.presence_of_element_located((By.CSS_SELECTOR, selector))).text
    except Exception:
        return None


porcentagem = getElement('text[class="chart__caption"]')
qtdTeste = getElement('.splash__title')
data = getElement('.widget__title')
qtdFalhas = getElement('div:nth-of-type(2) > div:nth-of-type(2) > div:nth-of-type(2) > div > div > a:nth-of-type(1) > '
                       'div:nth-of-type(2) > div > div') or "0"
tempoDecorrido = getElement('div:nth-child(1) > h2 > div')

driver.save_screenshot("allure_screenshot.png")

driver.quit()

webhook = DiscordWebhook(url=webhook_key)

embed = DiscordEmbed(
    title=title
)

embed.set_color("00FF00" if porcentagem == "100%" else "FF0000")

embed.set_url(f"{base_url}")
embed.add_embed_field("Quantidade de teste", qtdTeste, False)

if qtdFalhas != "0":
    embed.add_embed_field("Falhas", qtdFalhas, False)

tempo_decorrido_match = re.search(r"\((.*?)\)", tempoDecorrido)
if tempo_decorrido_match:
    embed.add_embed_field("Tempo decorrido", tempo_decorrido_match.group(1), False)

with open("./allure_screenshot.png", "rb") as f:
    webhook.add_file(file=f.read(), filename="allure_screenshot.png")
embed.set_image(url="attachment://allure_screenshot.png")

webhook.add_embed(embed)

webhook.execute()