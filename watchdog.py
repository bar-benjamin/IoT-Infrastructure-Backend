import subprocess
import time
import os
import logging

logging.basicConfig(level=logging.INFO)

pwd = os.getcwd()
command = './gradlew run'


def check_status():
    if is_server_running():
        logging.info("Server is running...")
        return True
    else:
        logging.info("Server is down. Restarting...")
        restart_server()
        logging.info("Server was restarted successfully!")
        return False


def is_server_running():
    try:
        output = subprocess.check_output(["ps", "-a"]).decode()
        lines = output.strip().split('\n')
        for line in lines:
            if "java" in line:
                return True
        return False
    except subprocess.CalledProcessError:
        return False


def restart_server():
    try:
        subprocess.run(['gnome-terminal', '--tab', '--', 'bash', '-c', f'cd {pwd} && {command}'], check=True)
    except (OSError, subprocess.SubprocessError):
        logging.error("Restart failed!")


def main():
    logging.info("Watchdog process started")

    while not is_server_running():
        logging.info("Please start up the server. Waiting...")
        time.sleep(3)

    while check_status():
        time.sleep(3)


if __name__ == "__main__":
    main()
